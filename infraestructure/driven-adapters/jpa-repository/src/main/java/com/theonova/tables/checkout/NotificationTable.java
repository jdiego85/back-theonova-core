package com.theonova.tables.checkout;

import com.theonova.enums.NotificationChannel;
import com.theonova.enums.NotificationStatus;
import com.theonova.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class NotificationTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 40)
    @Comment("Notification type")
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 20)
    @Comment("Notification channel")
    private NotificationChannel channel;

    @Column(name = "user_id")
    @Comment("User identifier")
    private Long userId;

    @Column(name = "order_id")
    @Comment("Order identifier")
    private Long orderId;

    @Column(name = "cart_id")
    @Comment("Cart identifier")
    private Long cartId;

    @Column(name = "payload", columnDefinition = "json")
    @Comment("Notification payload")
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Comment("Notification status")
    private NotificationStatus status;

    @Column(name = "scheduled_at", nullable = false)
    @Comment("Scheduled timestamp")
    private LocalDateTime scheduledAt;

    @Column(name = "sent_at")
    @Comment("Sent timestamp")
    private LocalDateTime sentAt;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;
}
