package com.theonova.entities.checkout;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record OrderCheckout(
    Long userId,
    String codeWarehouse,
    String shippingName,
    String shippingPhone,
    String shippingAddress,
    String currency,
    BigDecimal shipping,
    String notes
) {
}
