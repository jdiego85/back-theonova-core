package com.theonova.response.catalog;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String sku,
        String name,
        String description,
        BigDecimal price,
        Long brandId,
        boolean active,//is_active
        int minStock,
        int reorderPoint,
        int leadTimeDays
) {
}
