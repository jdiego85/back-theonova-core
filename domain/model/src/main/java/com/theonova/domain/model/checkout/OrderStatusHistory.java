package com.theonova.domain.model.checkout;

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
