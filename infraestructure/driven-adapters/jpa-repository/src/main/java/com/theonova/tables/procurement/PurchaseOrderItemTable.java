package com.theonova.tables.procurement;

import com.theonova.tables.catalog.ProductTable;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "purchase_order_items")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderItemTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    @Comment("Purchase order reference")
    private PurchaseOrderTable purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @Comment("Product reference")
    private ProductTable product;

    @Column(name = "quantity_ordered", nullable = false)
    @Comment("Ordered quantity")
    private int quantityOrdered;

    @Column(name = "quantity_received", nullable = false)
    @Comment("Received quantity")
    private int quantityReceived;

    @Column(name = "unit_cost", nullable = false, precision = 12, scale = 2)
    @Comment("Unit cost")
    private BigDecimal unitCost;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;
}
