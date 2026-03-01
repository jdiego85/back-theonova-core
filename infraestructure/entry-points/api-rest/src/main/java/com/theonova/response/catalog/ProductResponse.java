package com.theonova.response.catalog;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductResponse  {
        String sku;
        String name;
        String description;
        BigDecimal price;
        int minStock;
        int reorderPoint;
        int leadTimeDays;
}
