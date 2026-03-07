package com.theonova.business.checkout;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.entities.checkout.Cart;
import com.theonova.entities.checkout.CartItem;
import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.entities.inventory.StockReservation;
import com.theonova.enums.CartStatus;
import com.theonova.enums.ErrorCode;
import com.theonova.enums.ReservationStatus;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.gateways.checkout.CartGateway;
import com.theonova.gateways.checkout.CartItemGateway;
import com.theonova.gateways.inventory.InventoryBalanceGateway;
import com.theonova.gateways.inventory.StockReservationGateway;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class CartItemUseCase {

    private static final ZoneId GUAYAQUIL_ZONE = ZoneId.of("America/Guayaquil");

    private final CartItemGateway cartItemGateway;
    private final CartGateway cartGateway;
    private final ProductGateway productGateway;
    private final WarehouseGateway warehouseGateway;
    private final InventoryBalanceGateway inventoryBalanceGateway;
    private final StockReservationGateway stockReservationGateway;

    public CartItem execute(CartItem cartItem) {
        Product product = productGateway.findBySku(cartItem.skuProduct())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_PRODUCT_ID));
        Warehouse warehouse = warehouseGateway.findByCode(cartItem.codeWarehouse())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_WAREHOUSE_CODE));

        Cart activeCart = resolveOrCreateActiveCart(cartItem.userId());
        Optional<CartItem> existingItem = cartItemGateway.findByCartId(activeCart.id()).stream()
                .filter(item -> product.id().equals(item.productId()) && warehouse.id().equals(item.warehouseId()))
                .findFirst();
        int quantityToSave = existingItem
                .map(item -> item.quantity() + cartItem.quantity())
                .orElse(cartItem.quantity());
        validateInventoryAvailability(activeCart.id(), product.id(), warehouse.id(), quantityToSave);

        CartItem toSave = CartItem.builder()
                .id(existingItem.map(CartItem::id).orElse(null))
                .cartId(activeCart.id())
                .productId(product.id())
                .warehouseId(existingItem.map(CartItem::warehouseId).orElse(warehouse.id()))
                .userId(cartItem.userId())
                .skuProduct(product.sku())
                .codeWarehouse(warehouse.code())
                .quantity(quantityToSave)
                .build();

        return cartItemGateway.saveItem(toSave);
    }

    private void validateInventoryAvailability(Long cartId, Long productId, Long warehouseId, int requestedTotalQuantity) {
        InventoryBalance inventoryBalance = inventoryBalanceGateway.findByProductAndWarehouse(productId, warehouseId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INSUFFICIENT_STOCK));

        int activeReservationForCart = stockReservationGateway.findByCartId(cartId).stream()
                .filter(reservation -> ReservationStatus.ACTIVE.equals(reservation.status()))
                .filter(reservation -> Objects.equals(reservation.productId(), productId))
                .filter(reservation -> Objects.equals(reservation.warehouseId(), warehouseId))
                .map(StockReservation::quantity)
                .findFirst()
                .orElse(0);

        int availableForCart = (inventoryBalance.onHand() - inventoryBalance.reserved()) + activeReservationForCart;
        if (requestedTotalQuantity > availableForCart) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK);
        }
    }

    private Cart resolveOrCreateActiveCart(Long userId) {
        return cartGateway.findByUserId(userId).stream()
                .filter(cart -> CartStatus.ACTIVE.equals(cart.status()))
                .findFirst()
                .orElseGet(() -> cartGateway.saveItem(new Cart(
                        null,
                        userId,
                        CartStatus.ACTIVE,
                        ZonedDateTime.now(GUAYAQUIL_ZONE).toInstant(),
                        null
                )));
    }
}
