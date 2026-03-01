package com.theonova.tables.catalog;

import com.theonova.enums.AttributeDataType;
import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "attributes")
@Getter
@Setter
@NoArgsConstructor
public class AttributeEntity extends ConcurrencyEntity {
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

}
