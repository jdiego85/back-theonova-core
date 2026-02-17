package com.theonova.tables.catalog;

import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.Instant;

import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "countries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryEntity extends ConcurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "iso2", nullable = false, length = 2, unique = true)
    @Comment("ISO-2 country code")
    private String iso2;

    @Column(name = "name", nullable = false, length = 100)
    @Comment("Country name")
    private String name;

}
