package com.theonova.entities.checkout;

import com.theonova.enums.OrderStatus;
import com.theonova.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record Order(
        long id,
        String orderNumber,
        long userId,
        long warehouseId,
        OrderStatus status,
        PaymentStatus paymentStatus,
        String currency,
        BigDecimal subtotal,
        BigDecimal shipping,
        BigDecimal total,
        String shippingName,
        String shippingPhone,
        String shippingAddress,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
