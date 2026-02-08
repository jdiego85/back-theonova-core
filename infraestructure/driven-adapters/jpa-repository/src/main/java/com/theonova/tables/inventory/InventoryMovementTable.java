package com.theonova.tables.inventory;

import com.theonova.tables.catalog.ProductTable;
import com.theonova.tables.catalog.WarehouseTable;
import jakarta.persistence.*;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "inventory_movements")
@Getter
@Setter
@NoArgsConstructor
public class InventoryMovementTable {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 20)
    @Comment("Movement type")
    private InventoryMovementType movementType;

    @Column(name = "quantity", nullable = false)
    @Comment("Movement quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "ref_type", nullable = false, length = 30)
    @Comment("Reference type")
    private InventoryReferenceType refType;

    @Column(name = "ref_id")
    @Comment("Reference identifier")
    private Long refId;

    @Column(name = "note", length = 255)
    @Comment("Movement note")
    private String note;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;
}
