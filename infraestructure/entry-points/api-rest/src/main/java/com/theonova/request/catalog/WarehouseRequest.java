package com.theonova.request.catalog;

public record WarehouseRequest(
        long countryId,
        String code,
        String name,
        String city,
        String address,
        boolean active,
        boolean defaultWarehouse
) {
}
