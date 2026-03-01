package com.theonova.request.catalog;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

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
