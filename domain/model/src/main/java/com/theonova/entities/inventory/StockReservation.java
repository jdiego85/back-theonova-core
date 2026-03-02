package com.theonova.entities.inventory;

import com.theonova.enums.ReservationStatus;
import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record StockReservation(
        Long id,
        Long productId,
        Long warehouseId,
        Long orderId,
        Long cartId,
        int quantity,
        ReservationStatus status
) {}
