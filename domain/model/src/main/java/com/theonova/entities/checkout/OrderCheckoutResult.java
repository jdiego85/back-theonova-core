package com.theonova.entities.checkout;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record OrderCheckoutResult(
        Long orderId,
        String orderNumber,
        String status,
        String paymentStatus,
        BigDecimal subtotal,
        BigDecimal shipping,
        BigDecimal total,
        List<OrderCheckoutResultItem> items,
        LocalDateTime createdAt
) {
}
