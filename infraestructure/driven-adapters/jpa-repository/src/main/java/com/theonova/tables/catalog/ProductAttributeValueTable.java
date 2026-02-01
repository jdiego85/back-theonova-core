package com.theonova.tables.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class ProductAttributeValueTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "product_id", nullable = false)
    @Comment("Product identifier")
    private Long productId;

    @Column(name = "attribute_id", nullable = false)
    @Comment("Attribute identifier")
    private Long attributeId;

    @Column(name = "value_string", length = 255)
    @Comment("String value")
    private String valueString;

    @Column(name = "value_number", precision = 18, scale = 6)
    @Comment("Numeric value")
    private BigDecimal valueNumber;

    @Column(name = "value_boolean")
    @Comment("Boolean value")
    private Boolean valueBoolean;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;
}
