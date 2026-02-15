package com.theonova.response.catalog;

import lombok.Builder;

@Builder
public record WarehouseResponse(long countryId,
                                String code,
                                String name,
                                String city,
                                String address,
                                boolean active,
                                boolean defaultWarehouse) {
}
