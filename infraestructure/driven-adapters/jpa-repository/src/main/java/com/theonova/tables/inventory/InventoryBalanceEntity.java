package com.theonova.tables.inventory;

import com.theonova.tables.catalog.ProductEntity;
import com.theonova.tables.catalog.WarehouseEntity;
import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
import jakarta.persistence.*;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "inventory_balances")
@Getter
@Setter
@NoArgsConstructor
public class InventoryBalanceEntity extends ConcurrencyEntity {
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

    @Column(name = "on_hand", nullable = false)
    @Comment("On-hand quantity")
    private int onHand;

    @Column(name = "reserved", nullable = false)
    @Comment("Reserved quantity")
    private int reserved;

}
