package com.theonova.business.checkout;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.entities.checkout.Cart;
import com.theonova.entities.checkout.CartItem;
import com.theonova.entities.checkout.Order;
import com.theonova.entities.checkout.OrderCheckout;
import com.theonova.entities.checkout.OrderCheckoutResult;
import com.theonova.entities.checkout.OrderCheckoutResultItem;
import com.theonova.entities.checkout.OrderItem;
import com.theonova.entities.inventory.StockReservation;
import com.theonova.enums.CartStatus;
import com.theonova.enums.ErrorCode;
import com.theonova.enums.OrderStatus;
import com.theonova.enums.PaymentStatus;
import com.theonova.enums.ReservationStatus;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.gateways.checkout.CartGateway;
import com.theonova.gateways.checkout.CartItemGateway;
import com.theonova.gateways.checkout.OrderGateway;
import com.theonova.gateways.checkout.OrderItemGateway;
import com.theonova.gateways.inventory.StockReservationGateway;
import com.theonova.service.checkout.OrderCheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderCheckoutUseCase {

    private static final ZoneId GUAYAQUIL_ZONE = ZoneId.of("America/Guayaquil");

    private final WarehouseGateway warehouseGateway;
    private final ProductGateway productGateway;
    private final CartGateway cartGateway;
    private final CartItemGateway cartItemGateway;
    private final OrderGateway orderGateway;
    private final OrderItemGateway orderItemGateway;
    private final StockReservationGateway stockReservationGateway;
    private final OrderCheckoutService orderCheckoutService;

    @Transactional
    public OrderCheckoutResult execute(OrderCheckout checkout) {
        Warehouse warehouse = warehouseGateway.findByCode(checkout.codeWarehouse())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_WAREHOUSE_CODE));

        Cart activeCart = cartGateway.lockActiveByUserId(checkout.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));

        List<CartItem> cartItems = cartItemGateway.findByCartId(activeCart.id());
        if (cartItems.isEmpty()) {
            throw new BusinessException(ErrorCode.CART_EMPTY);
        }

        validateCartItemsBelongToWarehouse(cartItems, warehouse.id());

        List<StockReservation> activeReservations = stockReservationGateway.findByCartId(activeCart.id()).stream()
                .filter(reservation -> ReservationStatus.ACTIVE.equals(reservation.status()))
                .toList();
        validateReservationsCoverCartItems(cartItems, activeReservations);

        BigDecimal shipping = orderCheckoutService.normalizeAmount(checkout.shipping());
        String orderNumber = orderCheckoutService.generateUniqueOrderNumber(orderGateway);

        Order createdOrder = orderGateway.saveItem(new Order(
                0L,
                orderNumber,
                checkout.userId(),
                warehouse.id(),
                OrderStatus.PENDING,
                PaymentStatus.PENDING,
                orderCheckoutService.normalizeCurrency(checkout.currency()),
                BigDecimal.ZERO,
                shipping,
                shipping,
                checkout.shippingName(),
                checkout.shippingPhone(),
                checkout.shippingAddress(),
                checkout.notes(),
                null,
                null
        ));

        List<OrderCheckoutResultItem> responseItems = createOrderItems(createdOrder, cartItems);
        BigDecimal subtotal = orderCheckoutService.normalizeAmount(responseItems.stream()
                .map(OrderCheckoutResultItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        BigDecimal total = orderCheckoutService.normalizeAmount(subtotal.add(shipping));

        Order finalizedOrder = orderGateway.saveItem(new Order(
                createdOrder.id(),
                createdOrder.orderNumber(),
                createdOrder.userId(),
                createdOrder.warehouseId(),
                createdOrder.status(),
                createdOrder.paymentStatus(),
                createdOrder.currency(),
                subtotal,
                shipping,
                total,
                createdOrder.shippingName(),
                createdOrder.shippingPhone(),
                createdOrder.shippingAddress(),
                createdOrder.notes(),
                createdOrder.createdAt(),
                createdOrder.updatedAt()
        ));

        Instant now = Instant.now();
        cartGateway.saveItem(activeCart.toBuilder()
                .status(CartStatus.CHECKED_OUT)
                .lastActivityAt(now)
                .build());

        consumeReservations(activeReservations, cartItems, finalizedOrder.id());

        LocalDateTime createdAt = finalizedOrder.createdAt() != null
                ? LocalDateTime.ofInstant(finalizedOrder.createdAt(), GUAYAQUIL_ZONE)
                : LocalDateTime.ofInstant(now, GUAYAQUIL_ZONE);

        return OrderCheckoutResult.builder()
                .orderId(finalizedOrder.id())
                .orderNumber(finalizedOrder.orderNumber())
                .status(finalizedOrder.status().name())
                .paymentStatus(finalizedOrder.paymentStatus().name())
                .subtotal(subtotal)
                .shipping(shipping)
                .total(total)
                .items(responseItems)
                .createdAt(createdAt)
                .build();
    }

    private void validateCartItemsBelongToWarehouse(List<CartItem> cartItems, Long checkoutWarehouseId) {
        boolean mismatch = cartItems.stream()
                .anyMatch(item -> !Objects.equals(item.warehouseId(), checkoutWarehouseId));
        if (mismatch) {
            throw new BusinessException(ErrorCode.CART_WAREHOUSE_MISMATCH);
        }
    }

    private void validateReservationsCoverCartItems(List<CartItem> cartItems, List<StockReservation> activeReservations) {
        Map<String, StockReservation> reservationByKey = activeReservations.stream()
                .collect(Collectors.toMap(
                        reservation -> reservation.productId() + ":" + reservation.warehouseId(),
                        Function.identity(),
                        (first, second) -> second
                ));

        for (CartItem cartItem : cartItems) {
            String key = cartItem.productId() + ":" + cartItem.warehouseId();
            StockReservation reservation = reservationByKey.get(key);
            if (reservation == null || reservation.quantity() < cartItem.quantity()) {
                throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK);
            }
        }
    }

    private List<OrderCheckoutResultItem> createOrderItems(Order createdOrder, List<CartItem> cartItems) {
        List<OrderCheckoutResultItem> responseItems = new ArrayList<>();
        List<CartItem> orderedCartItems = cartItems.stream()
                .sorted(Comparator.comparing(CartItem::id))
                .toList();

        for (CartItem cartItem : orderedCartItems) {
            Product product = productGateway.findById(cartItem.productId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_PRODUCT_ID));

            BigDecimal unitPrice = orderCheckoutService.normalizeAmount(product.price());
            BigDecimal lineTotal = orderCheckoutService.calculateLineTotal(cartItem.quantity(), unitPrice);

            orderItemGateway.saveItem(new OrderItem(
                    0L,
                    createdOrder.id(),
                    product.id(),
                    cartItem.quantity(),
                    unitPrice,
                    lineTotal,
                    null
            ));

            responseItems.add(OrderCheckoutResultItem.builder()
                    .skuProduct(product.sku())
                    .productName(product.name())
                    .quantity(cartItem.quantity())
                    .unitPrice(unitPrice)
                    .lineTotal(lineTotal)
                    .build());
        }

        return responseItems;
    }

    private void consumeReservations(List<StockReservation> activeReservations, List<CartItem> cartItems, long orderId) {
        Map<String, CartItem> cartItemsByKey = cartItems.stream()
                .collect(Collectors.toMap(
                        item -> item.productId() + ":" + item.warehouseId(),
                        Function.identity(),
                        (first, second) -> second
                ));

        for (StockReservation reservation : activeReservations) {
            String key = reservation.productId() + ":" + reservation.warehouseId();
            CartItem cartItem = cartItemsByKey.get(key);
            if (cartItem == null) {
                continue;
            }

            stockReservationGateway.saveItem(reservation.toBuilder()
                    .orderId(orderId)
                    .quantity(cartItem.quantity())
                    .status(ReservationStatus.CONSUMED)
                    .build());
        }
    }
}
