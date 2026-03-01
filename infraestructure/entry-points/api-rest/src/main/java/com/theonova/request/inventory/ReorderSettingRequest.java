package com.theonova.request.inventory;

import com.theonova.exceptions.dto.CommonDataEntry;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReorderSettingRequest extends CommonDataEntry {
    @NotBlank(message = "skuProduct is required")
    private String skuProduct;
    @NotBlank(message = "codeWarehouse is required")
    private String codeWarehouse;
    @Min(value = 1, message = "threshold must be greater than or equal to 1")
    private int threshold;
}
