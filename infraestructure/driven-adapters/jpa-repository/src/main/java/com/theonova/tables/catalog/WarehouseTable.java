package com.theonova.tables.catalog;

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
public class WarehouseTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "country_id", nullable = false)
    @Comment("Country identifier")
    private Long countryId;

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

    @Column(name = "is_active", nullable = false)
    @Comment("Active flag")
    private boolean active;

    @Column(name = "is_default", nullable = false)
    @Comment("Default warehouse flag")
    private boolean defaultWarehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false, insertable = false, updatable = false)
    @Comment("Country reference")
    private CountryTable country;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @Comment("Last update timestamp")
    private Instant updatedAt;
}
