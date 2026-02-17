package com.theonova.entities.catalog;

import lombok.Builder;

import java.time.Instant;

@Builder
public record Warehouse(

        long countryId,
        String code,
        String name,
        String city,
        String address,
        boolean active,//is_active
        boolean defaultWarehouse,//is_default
        Instant createdAt,
        Instant updatedAt
) {
}
