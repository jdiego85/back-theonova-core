package com.theonova.tables.catalog;

import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
import jakarta.persistence.*;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
public class WarehouseEntity extends ConcurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id",nullable = false)
    @Comment("Country reference")
    private CountryEntity country;

    @Column(name = "code", nullable = false, length = 30)
    @Comment("Warehouse code")
    private String code;

    @Column(name = "name", nullable = false, length = 120)
    @Comment("Warehouse name")
    private String name;

    @Column(name = "city", length = 120)
    @Comment("City")
    private String city;

    @Column(name = "address", length = 255)
    @Comment("Address")
    private String address;

    @Column(name = "is_default", nullable = false)
    @Comment("Default warehouse flag")
    private boolean defaultWarehouse;

}
