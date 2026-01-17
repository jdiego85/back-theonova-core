package com.theonova.domain.model.checkout;

import java.time.Instant;

public record CartItem(
        long id,
        long cartId,
        long productId,
        long warehouseId,
        int quantity,
        Instant createdAt,
        Instant updatedAt
) {
}
