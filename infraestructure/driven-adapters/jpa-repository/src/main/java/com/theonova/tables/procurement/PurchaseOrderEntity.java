package com.theonova.tables.procurement;

import com.theonova.enums.PurchaseOrderStatus;
import com.theonova.tables.catalog.WarehouseEntity;
import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
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
public class PurchaseOrderEntity extends ConcurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    @Comment("Supplier reference")
    private SupplierEntity supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    @Comment("Warehouse reference")
    private WarehouseEntity warehouse;

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

}
