package com.theonova.entities.checkout;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record OrderCheckoutResultItem(
        String skuProduct,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
