package com.theonova.tables.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductCategoryId implements Serializable {
    @Column(name = "product_id", nullable = false)
    @Comment("Product identifier")
    private Long productId;

    @Column(name = "category_id", nullable = false)
    @Comment("Category identifier")
    private Long categoryId;
}
