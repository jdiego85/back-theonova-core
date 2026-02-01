package com.theonova.entities.inventory;

import com.theonova.enums.InventoryMovementType;
import com.theonova.enums.InventoryReferenceType;

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
