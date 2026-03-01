package com.theonova.entities.catalog;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder(toBuilder = true)
public record Product(
        Long id,
        String sku,
        String name,
        String description,
        BigDecimal price,
        Long brandId,
        int minStock,
        int reorderPoint,
        int leadTimeDays
       ) {}
