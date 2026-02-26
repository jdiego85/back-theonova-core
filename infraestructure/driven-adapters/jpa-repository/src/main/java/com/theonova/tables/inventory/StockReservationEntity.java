package com.theonova.tables.inventory;

import com.theonova.ReservationStatus;
import com.theonova.tables.catalog.ProductEntity;
import com.theonova.tables.catalog.WarehouseEntity;
import com.theonova.tables.checkout.CartEntity;
import com.theonova.tables.checkout.OrderEntity;
import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
import jakarta.persistence.*;

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
public class StockReservationEntity extends ConcurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @Comment("Product reference")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    @Comment("Warehouse reference")
    private WarehouseEntity warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Comment("Order reference")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @Comment("Cart reference")
    private CartEntity cart;

    @Column(name = "quantity", nullable = false)
    @Comment("Reserved quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Comment("Reservation status")
    private ReservationStatus status;

    @Column(name = "expires_at", nullable = false)
    @Comment("Expiration timestamp")
    private LocalDateTime expiresAt;

}
