package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Warehouse;
import com.theonova.request.catalog.WarehouseRequest;
import com.theonova.response.catalog.WarehouseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    @Mapping(target = "defaultWarehouse", constant = "true")
    Warehouse mapperRequestToDomain(WarehouseRequest warehouseRequest);
    WarehouseResponse mapperDomainToResponse(Warehouse warehouse);

}
