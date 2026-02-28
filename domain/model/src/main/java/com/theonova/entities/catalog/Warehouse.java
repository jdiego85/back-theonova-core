package com.theonova.entities.catalog;

import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record Warehouse(
        Long countryId,
        String iso2,
        String code,
        String name,
        String city,
        String address,
        boolean defaultWarehouse//is_default
        ) {}
