package com.theonova.response.checkout;

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
