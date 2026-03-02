package com.theonova.response.runtime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponse {
    private String skuProduct;
    private String codeWarehouse;
    private Long userId;
    private int quantity;
}
