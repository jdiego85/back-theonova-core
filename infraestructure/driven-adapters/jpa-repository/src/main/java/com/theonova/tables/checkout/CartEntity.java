package com.theonova.tables.checkout;

import com.theonova.enums.CartStatus;
import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
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
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
public class CartEntity extends ConcurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "user_id", nullable = false)
    @Comment("User identifier")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Comment("Cart status")
    private CartStatus status;

    @Column(name = "last_activity_at", nullable = false)
    @Comment("Last activity timestamp")
    private LocalDateTime lastActivityAt;

    @Column(name = "abandoned_at")
    @Comment("Abandoned timestamp")
    private LocalDateTime abandonedAt;

}
