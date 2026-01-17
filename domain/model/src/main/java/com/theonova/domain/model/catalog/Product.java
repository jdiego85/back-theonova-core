package com.theonova.domain.model.catalog;

import java.math.BigDecimal;
import java.time.Instant;

public record Product(
        long id,
        String sku,
        String name,
        String description,
        BigDecimal price,
        Long brandId,
        boolean active,
        int minStock,
        int reorderPoint,
        int leadTimeDays,
        Instant createdAt,
        Instant updatedAt
) {
}
