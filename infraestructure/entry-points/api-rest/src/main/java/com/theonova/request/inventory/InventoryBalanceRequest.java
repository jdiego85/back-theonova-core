package com.theonova.request.inventory;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Data;

@Data
public class InventoryBalanceRequest extends CommonDataEntry {
    String skuProduct;
    String codeWarehouse;
    int onHand;
    int reserved;
}
