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
@Table(name = "reorder_settings")
@Getter
@Setter
@NoArgsConstructor
public class ReorderSettingTable {
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


    @Column(name = "threshold", nullable = false)
    @Comment("Reorder threshold")
    private int threshold;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @Comment("Last update timestamp")
    private Instant updatedAt;
}
