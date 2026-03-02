package com.theonova.request.admin;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest extends CommonDataEntry {
        String sku;
        String name;
        String description;
        BigDecimal price;
        Long brandId;
        int minStock;
        int reorderPoint;
        int leadTimeDays;
}
