package com.theonova.domain.model.catalog;

import java.time.Instant;

public record Warehouse(
        long id,
        long countryId,
        String code,
        String name,
        String city,
        String address,
        boolean active,
        boolean defaultWarehouse,
        Instant createdAt,
        Instant updatedAt
) {
}
