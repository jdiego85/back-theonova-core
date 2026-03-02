package com.theonova.business.checkout;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.entities.checkout.Cart;
import com.theonova.entities.checkout.CartItem;
import com.theonova.enums.CartStatus;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.gateways.checkout.CartGateway;
import com.theonova.gateways.checkout.CartItemGateway;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class CartItemUseCase {

    private static final ZoneId GUAYAQUIL_ZONE = ZoneId.of("America/Guayaquil");

    private final CartItemGateway cartItemGateway;
    private final CartGateway cartGateway;
    private final ProductGateway productGateway;
    private final WarehouseGateway warehouseGateway;

    public CartItem execute(CartItem cartItem) {
        Product product = productGateway.findBySku(cartItem.skuProduct())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_PRODUCT_ID));
        Warehouse warehouse = warehouseGateway.findByCode(cartItem.codeWarehouse())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_WAREHOUSE_CODE));

        //Resuelve el carrito activo del userId: si el usuario no tiene carrito ACTIVE, crea uno nuevo con fecha now en zona America/Guayaquil
        Cart activeCart = resolveOrCreateActiveCart(cartItem.userId());
        //Busca si ya existe un CartItem en ese carrito para el mismo productId y el mismo codeWarehouse.
        Optional<CartItem> existingItem = cartItemGateway.findByCartId(activeCart.id()).stream()
                .filter(item -> product.id().equals(item.productId()) && warehouse.id().equals(item.warehouseId()))
                .findFirst();
        //Calcula la cantidad a guardar: si existía, suma existing.quantity + cartItem.quantity; si no, usa cartItem.quantity.
        int quantityToSave = existingItem
                .map(item -> item.quantity() + cartItem.quantity())
                .orElse(cartItem.quantity());
        //Construye el objeto a persistir: si existía, reutiliza el id (para actualizar) y también copia warehouseId del existente; si no existía, deja esos campos en null.
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
