package com.theonova.tables.checkout;

import com.theonova.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "order_status_history")
@Getter
@Setter
@NoArgsConstructor
public class OrderStatusHistoryTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @Comment("Order reference")
    private OrderTable order;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_status", length = 20)
    @Comment("Previous status")
    private OrderStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_status", nullable = false, length = 20)
    @Comment("New status")
    private OrderStatus toStatus;

    @Column(name = "changed_by")
    @Comment("User who changed the status")
    private Long changedBy;

    @Column(name = "changed_at", nullable = false)
    @Comment("Change timestamp")
    private Instant changedAt;
}
