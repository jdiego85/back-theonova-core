package com.theonova.response.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryBalanceResponse {
    String skuProduct;
    String codeWarehouse;
    int onHand;
    int reserved;

}
