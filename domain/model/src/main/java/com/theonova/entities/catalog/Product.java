package com.theonova.entities.catalog;

import java.math.BigDecimal;
import java.time.Instant;

public record Product(
        long id,
        String sku,
        String name,
        String description,
        BigDecimal price,
        Long brandId,
        boolean active,//is_active
        int minStock,
        int reorderPoint,
        int leadTimeDays,
        Instant createdAt,
        Instant updatedAt
) {
}
