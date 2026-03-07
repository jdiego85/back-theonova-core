package com.theonova.request.checkout;

import com.theonova.exceptions.dto.CommonDataEntry;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CartRequest extends CommonDataEntry {
    @NotNull(message = "userId is required")
    @Positive(message = "userId must be greater than 0")
    private Long userId;
}
