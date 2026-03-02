package com.theonova.entities.inventory;

import com.theonova.enums.InventoryMovementType;
import com.theonova.enums.InventoryReferenceType;
import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record InventoryMovement(
        Long id,
        Long productId,
        Long warehouseId,
        InventoryMovementType movementType,
        int quantity,
        InventoryReferenceType refType,
        Long refId,
        String note
) {
}
