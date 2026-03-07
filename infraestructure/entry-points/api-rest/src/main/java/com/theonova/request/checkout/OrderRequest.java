package com.theonova.request.checkout;

import com.theonova.exceptions.dto.CommonDataEntry;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderRequest extends CommonDataEntry {

    @NotBlank(message = "codeWarehouse is required")
    private String codeWarehouse;

    @NotNull(message = "userId is required")
    @Positive(message = "userId must be greater than 0")
    private Long userId;

    @NotBlank(message = "shippingName is required")
    private String shippingName;

    @NotBlank(message = "shippingPhone is required")
    private String shippingPhone;

    @NotBlank(message = "shippingAddress is required")
    private String shippingAddress;

    @NotBlank(message = "currency is required")
    @Size(min = 3, max = 3, message = "currency must have 3 characters")
    private String currency; // USD

    @PositiveOrZero(message = "shipping must be greater or equal than 0")
    private BigDecimal shipping;

    private String notes;
}
