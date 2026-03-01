package com.theonova.entities.checkout;

import com.theonova.enums.CartStatus;
import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record Cart(
        Long id,
        Long userId,
        CartStatus status,
        Instant lastActivityAt,
        Instant abandonedAt
) {}
