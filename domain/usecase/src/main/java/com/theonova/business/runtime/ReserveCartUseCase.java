package com.theonova.business.runtime;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.entities.checkout.Cart;
import com.theonova.entities.checkout.CartItem;
import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.entities.inventory.InventoryMovement;
import com.theonova.entities.inventory.StockReservation;
import com.theonova.entities.runtime.ReserveCart;
import com.theonova.entities.runtime.ReservedCart;
import com.theonova.entities.runtime.ReservedItem;
import com.theonova.enums.CartStatus;
import com.theonova.enums.ErrorCode;
import com.theonova.enums.InventoryMovementType;
import com.theonova.enums.InventoryReferenceType;
import com.theonova.enums.ReservationStatus;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.gateways.checkout.CartGateway;
import com.theonova.gateways.checkout.CartItemGateway;
import com.theonova.gateways.inventory.InventoryBalanceGateway;
import com.theonova.gateways.inventory.InventoryMovementGateway;
import com.theonova.gateways.inventory.StockReservationGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class ReserveCartUseCase {

    private static final ZoneId GUAYAQUIL_ZONE = ZoneId.of("America/Guayaquil");

    private final ProductGateway productGateway;
    private final WarehouseGateway warehouseGateway;
    private final CartGateway cartGateway;
    private final CartItemGateway cartItemGateway;
    private final InventoryBalanceGateway inventoryBalanceGateway;
    private final StockReservationGateway stockReservationGateway;
    private final InventoryMovementGateway inventoryMovementGateway;

    @Transactional
    public ReservedCart execute(ReserveCart reserveCart) {
        Product product = productGateway.findBySku(reserveCart.skuProduct())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_PRODUCT_ID));
        Warehouse warehouse = warehouseGateway.findByCode(reserveCart.codeWarehouse())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_WAREHOUSE_CODE));

        Cart activeCart = cartGateway.findByUserId(reserveCart.userId()).stream()
                .filter(cart -> CartStatus.ACTIVE.equals(cart.status()))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR));

        CartItem cartItem = cartItemGateway.findByCartId(activeCart.id()).stream()
                .filter(item -> Objects.equals(item.productId(), product.id())
                        && Objects.equals(item.warehouseId(), warehouse.id()))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR));

        InventoryBalance lockedBalance = inventoryBalanceGateway.lockByProductAndWarehouse(product.id(), warehouse.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.INSUFFICIENT_STOCK));

        int reservationQuantity = cartItem.quantity();
        int available = lockedBalance.onHand() - lockedBalance.reserved();
        if (available < reservationQuantity) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK);
        }

        inventoryBalanceGateway.saveItem(lockedBalance.toBuilder()
                .reserved(lockedBalance.reserved() + reservationQuantity)
                .build());

        Optional<StockReservation> existingReservation = stockReservationGateway.findByCartId(activeCart.id()).stream()
                .filter(reservation -> Objects.equals(reservation.productId(), product.id())
                        && Objects.equals(reservation.warehouseId(), warehouse.id())
                        && ReservationStatus.ACTIVE.equals(reservation.status()))
                .findFirst();

        stockReservationGateway.saveItem(StockReservation.builder()
                .id(existingReservation.map(StockReservation::id).orElse(null))
                .productId(product.id())
                .warehouseId(warehouse.id())
                .cartId(activeCart.id())
                .orderId(null)
                .quantity(reservationQuantity)
                .status(ReservationStatus.ACTIVE)
                .build());

        inventoryMovementGateway.saveItem(InventoryMovement.builder()
                .productId(product.id())
                .warehouseId(warehouse.id())
                .movementType(InventoryMovementType.RESERVE)
                .quantity(reservationQuantity)
                .refType(InventoryReferenceType.CART)
                .refId(activeCart.id())
                .note("Stock reserved from ReserveCartUseCase")
                .build());

        return ReservedCart.builder()
                .reservationStatus("RESERVED")
                .reservedItems(List.of(
                        ReservedItem.builder()
                                .skuProduct(product.sku())
                                .codeWarehouse(warehouse.code())
                                .reservedQuantity(reservationQuantity)
                                .build()
                ))
                .expiresAt(LocalDateTime.now(GUAYAQUIL_ZONE).plusMinutes(30))
                .build();
    }
}
