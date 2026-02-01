package com.theonova.entities.checkout;

import com.theonova.enums.CartStatus;

import java.time.Instant;

public record Cart(
        long id,
        long userId,
        CartStatus status,
        Instant lastActivityAt,
        Instant abandonedAt,
        Instant createdAt,
        Instant updatedAt
) {
}
