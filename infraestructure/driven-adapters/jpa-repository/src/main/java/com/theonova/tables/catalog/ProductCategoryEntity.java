package com.theonova.tables.catalog;

import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_categories")
@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryEntity {
    @EmbeddedId
    private ProductCategoryId id;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @MapsId("categoryId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
}
