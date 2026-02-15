package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Warehouse;
import com.theonova.request.catalog.WarehouseRequest;
import com.theonova.response.catalog.WarehouseResponse;

public class WarehouseMapper {
    public static Warehouse requestToDomain(WarehouseRequest warehouseRequest){
        return Warehouse.builder()
                .countryId(warehouseRequest.countryId())
                .code(warehouseRequest.code())
                .name(warehouseRequest.name())
                .city(warehouseRequest.city())
                .address(warehouseRequest.address())
                .active(warehouseRequest.active())
                .defaultWarehouse(warehouseRequest.defaultWarehouse())
                .build();
    }
    public static WarehouseResponse domainToResponse(Warehouse warehouse){
        return WarehouseResponse.builder()
                .countryId(warehouse.countryId())
                .code(warehouse.code())
                .city(warehouse.city())
                .address(warehouse.address())
                .active(warehouse.active())
                .defaultWarehouse(warehouse.defaultWarehouse())
                .build();
    }
}
