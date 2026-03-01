package com.theonova.response.inventory;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReorderSettingResponse {
    private String skuProduct;
    private String codeWarehouse;
    private int threshold;
}
