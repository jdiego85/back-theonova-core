package com.theonova.tables.inventory;

import com.theonova.tables.catalog.ProductTable;
import com.theonova.tables.catalog.WarehouseTable;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "stock_reservations")
@Getter
@Setter
@NoArgsConstructor
public class StockReservationTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @Comment("Product reference")
    private ProductTable product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    @Comment("Warehouse reference")
    private WarehouseTable warehouse;

    @Column(name = "order_id")
    @Comment("Order identifier")
    private Long orderId;

    @Column(name = "cart_id")
    @Comment("Cart identifier")
    private Long cartId;

    @Column(name = "quantity", nullable = false)
    @Comment("Reserved quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Comment("Reservation status")
    private StockReservationStatus status;

    @Column(name = "expires_at", nullable = false)
    @Comment("Expiration timestamp")
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;
}
