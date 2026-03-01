package com.theonova.request.catalog;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Data;

@Data
public class ProductCategoryRequest extends CommonDataEntry{
    String slug;
    String sku;
}
