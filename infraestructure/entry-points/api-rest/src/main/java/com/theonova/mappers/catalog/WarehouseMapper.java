package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Warehouse;
import com.theonova.request.catalog.WarehouseRequest;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.catalog.WarehouseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    @Mapping(target = "defaultWarehouse", constant = "true")
    Warehouse mapperRequestToDomain(WarehouseRequest warehouseRequest);
    WarehouseResponse mapperDomainToData(Warehouse warehouse);
    default ApiResponseWrapper<WarehouseResponse> domainToResponse(Warehouse warehouse) {
        return ApiResponseWrapper.wrapUp("warehouseService", mapperDomainToData(warehouse));
    }
}
