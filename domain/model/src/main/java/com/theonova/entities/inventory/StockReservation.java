package com.theonova.entities.inventory;

import com.theonova.ReservationStatus;

import java.time.Instant;

public record StockReservation(
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
