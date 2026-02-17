package com.theonova.tables.catalog;

import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "product_attribute_values")
@Getter
@Setter
@NoArgsConstructor
public class ProductAttributeValueEntity extends ConcurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    @Comment("Product reference")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id",nullable = false)
    @Comment("Attribute reference")
    private AttributeEntity attribute;

    @Column(name = "value_string", length = 255)
    @Comment("String value")
    private String valueString;

    @Column(name = "value_number", precision = 18, scale = 6)
    @Comment("Numeric value")
    private BigDecimal valueNumber;

    @Column(name = "value_boolean")
    @Comment("Boolean value")
    private Boolean valueBoolean;

}
