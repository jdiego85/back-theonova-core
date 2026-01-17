package com.theonova.entities.catalog;

import java.time.Instant;

public record Warehouse(
        long id,
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
