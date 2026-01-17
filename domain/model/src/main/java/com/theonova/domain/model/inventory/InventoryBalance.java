package com.theonova.domain.model.inventory;

import java.time.Instant;

public record InventoryBalance(
        long id,
        long productId,
        long warehouseId,
        int onHand,
        int reserved,
        Instant updatedAt
) {
}
