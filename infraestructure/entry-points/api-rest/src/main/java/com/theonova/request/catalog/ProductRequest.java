package com.theonova.request.catalog;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class ProductRequest extends CommonDataEntry {
        String sku;
        String name;
        String description;
        BigDecimal price;
        Long brandId;
        boolean active;//is_active
        int minStock;
        int reorderPoint;
        int leadTimeDays;
}
