package com.theonova.entities.checkout;

import com.theonova.enums.NotificationChannel;
import com.theonova.enums.NotificationStatus;
import com.theonova.enums.NotificationType;

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
