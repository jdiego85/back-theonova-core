package com.theonova.tables.procurement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "purchase_orders")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "supplier_id", nullable = false)
    @Comment("Supplier identifier")
    private Long supplierId;

    @Column(name = "warehouse_id", nullable = false)
    @Comment("Warehouse identifier")
    private Long warehouseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    @Comment("Purchase order status")
    private PurchaseOrderStatus status;

    @Column(name = "order_date", nullable = false)
    @Comment("Order date")
    private LocalDate orderDate;

    @Column(name = "expected_date")
    @Comment("Expected receipt date")
    private LocalDate expectedDate;

    @Column(name = "received_date")
    @Comment("Received date")
    private LocalDate receivedDate;

    @Column(name = "total_cost", nullable = false, precision = 12, scale = 2)
    @Comment("Total cost")
    private BigDecimal totalCost;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @Comment("Last update timestamp")
    private Instant updatedAt;
}
