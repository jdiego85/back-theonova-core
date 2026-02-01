package com.theonova.tables.checkout;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
public class CartItemTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "cart_id", nullable = false)
    @Comment("Cart identifier")
    private Long cartId;

    @Column(name = "product_id", nullable = false)
    @Comment("Product identifier")
    private Long productId;

    @Column(name = "warehouse_id", nullable = false)
    @Comment("Warehouse identifier")
    private Long warehouseId;

    @Column(name = "quantity", nullable = false)
    @Comment("Item quantity")
    private int quantity;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @Comment("Last update timestamp")
    private Instant updatedAt;
}
