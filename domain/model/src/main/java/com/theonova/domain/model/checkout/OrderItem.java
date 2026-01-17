package com.theonova.domain.model.checkout;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderItem(
        long id,
        long orderId,
        long productId,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal,
        Instant createdAt
) {
}
