package com.theonova.entities.inventory;

import java.time.Instant;

public record InventoryMovement(
        long id,
        long productId,
        long warehouseId,
        InventoryMovementType movementType,
        int quantity,
        InventoryReferenceType refType,
        Long refId,
        String note,
        Instant createdAt
) {
}
