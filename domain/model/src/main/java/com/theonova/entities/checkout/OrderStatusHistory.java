package com.theonova.entities.checkout;

import com.theonova.enums.OrderStatus;

import java.time.Instant;

public record OrderStatusHistory(
        long id,
        long orderId,
        OrderStatus fromStatus,
        OrderStatus toStatus,
        Long changedBy,
        Instant changedAt
) {
}
