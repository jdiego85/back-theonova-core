package com.theonova.service.inventory;

import com.theonova.enums.ReservationStatus;

import java.time.Instant;

public record StockReservationServiceAdapter(
        long id,
        long productId,
        long warehouseId,
        Long orderId,
        Long cartId,
        int quantity,
        ReservationStatus status,
        Instant expiresAt,
        Instant createdAt
) {
}
