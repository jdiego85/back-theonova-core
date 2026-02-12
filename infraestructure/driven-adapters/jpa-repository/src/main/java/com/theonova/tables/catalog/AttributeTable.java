package com.theonova.tables.catalog;

import com.theonova.enums.AttributeDataType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
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
@Table(name = "attributes")
@Getter
@Setter
@NoArgsConstructor
public class AttributeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "name", nullable = false, length = 120)
    @Comment("Attribute name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false, length = 20)
    @Comment("Attribute data type")
    private AttributeDataType dataType;

    @Column(name = "is_filterable", nullable = false)
    @Comment("Filterable flag")
    private boolean filterable;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;
}
