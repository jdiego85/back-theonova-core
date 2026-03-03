package com.theonova.entities.runtime;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReservedCart(
        String reservationStatus,
        List<ReservedItem> reservedItems,
        LocalDateTime expiresAt
) {}
