package com.theonova.entities.checkout;

import lombok.Builder;

@Builder(toBuilder = true)
public record CartItem(
        Long id,
        Long cartId,
        Long productId,
        Long warehouseId,
        Long userId,
        String skuProduct,
        String codeWarehouse,
        int quantity
) {}
