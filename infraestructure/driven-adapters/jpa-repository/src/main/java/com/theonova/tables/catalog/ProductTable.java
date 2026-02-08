package com.theonova.tables.catalog;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "sku", nullable = false, length = 64, unique = true)
    @Comment("Stock keeping unit")
    private String sku;

    @Column(name = "name", nullable = false, length = 180)
    @Comment("Product name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    @Comment("Product description")
    private String description;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    @Comment("Unit price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id",nullable = false)
    @Comment("Brand reference")
    private BrandTable brand;

    @Column(name = "is_active", nullable = false)
    @Comment("Active flag")
    private boolean active;

    @Column(name = "min_stock", nullable = false)
    @Comment("Minimum stock")
    private int minStock;

    @Column(name = "reorder_point", nullable = false)
    @Comment("Reorder point")
    private int reorderPoint;

    @Column(name = "lead_time_days", nullable = false)
    @Comment("Lead time in days")
    private int leadTimeDays;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @Comment("Last update timestamp")
    private Instant updatedAt;
}
