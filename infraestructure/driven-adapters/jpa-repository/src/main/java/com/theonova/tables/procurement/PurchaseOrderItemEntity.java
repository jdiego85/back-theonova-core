package com.theonova.tables.procurement;

import com.theonova.tables.catalog.ProductEntity;
import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
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
public class PurchaseOrderItemEntity extends ConcurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    @Comment("Purchase order reference")
    private PurchaseOrderEntity purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @Comment("Product reference")
    private ProductEntity product;

    @Column(name = "quantity_ordered", nullable = false)
    @Comment("Ordered quantity")
    private int quantityOrdered;

    @Column(name = "quantity_received", nullable = false)
    @Comment("Received quantity")
    private int quantityReceived;

    @Column(name = "unit_cost", nullable = false, precision = 12, scale = 2)
    @Comment("Unit cost")
    private BigDecimal unitCost;

}
