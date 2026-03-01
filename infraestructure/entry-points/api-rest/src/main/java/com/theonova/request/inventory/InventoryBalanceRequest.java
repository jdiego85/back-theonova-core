package com.theonova.request.inventory;

import com.theonova.exceptions.dto.CommonDataEntry;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryBalanceRequest extends CommonDataEntry {
    @NotBlank(message = "skuProduct is required")
    private String skuProduct;

    @NotBlank(message = "codeWarehouse is required")
    private String codeWarehouse;

    @Min(value = 0, message = "onHand must be greater than or equal to 0")
    private int onHand;

    @Min(value = 0, message = "reserved must be greater than or equal to 0")
    private int reserved;
}
