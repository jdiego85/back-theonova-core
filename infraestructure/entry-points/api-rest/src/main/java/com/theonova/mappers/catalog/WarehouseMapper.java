package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Warehouse;
import com.theonova.request.admin.WarehouseRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.admin.WarehouseResponse;
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
