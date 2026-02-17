package com.theonova.response.catalog;

import com.theonova.exceptions.dto.CommonDataOutput;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class ProductResponse  extends CommonDataOutput {
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
