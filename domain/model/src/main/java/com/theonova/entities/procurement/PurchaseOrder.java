package com.theonova.entities.procurement;

import com.theonova.enums.PurchaseOrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record PurchaseOrder(
        long id,
        long supplierId,
        long warehouseId,
        PurchaseOrderStatus status,
        LocalDate orderDate,
        LocalDate expectedDate,
        LocalDate receivedDate,
        BigDecimal totalCost,
        Instant createdAt,
        Instant updatedAt
) {
}
