package com.theonova.entities.checkout;

import com.theonova.NotificationChannel;
import com.theonova.NotificationStatus;
import com.theonova.NotificationType;

import java.time.Instant;
import java.util.Map;

public record Notification(
        long id,
        NotificationType type,
        NotificationChannel channel,
        Long userId,
        Long orderId,
        Long cartId,
        Map<String, Object> payload,
        NotificationStatus status,
        Instant scheduledAt,
        Instant sentAt,
        Instant createdAt
) {
}
