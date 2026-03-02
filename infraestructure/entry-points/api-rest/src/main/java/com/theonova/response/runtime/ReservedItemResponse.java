package com.theonova.response.runtime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservedItemResponse {
    private String skuProduct;
    private String codeWarehouse;
    private int reservedQuantity;
}
