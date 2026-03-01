package com.theonova.mappers.inventory;

import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.request.inventory.InventoryBalanceRequest;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.inventory.InventoryBalanceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryBalanceMapper {
    InventoryBalance requestToDomain(InventoryBalanceRequest inventoryBalanceRequest);
    InventoryBalanceResponse domainToResponse(InventoryBalance inventoryBalance);
    default ApiResponseWrapper<InventoryBalanceResponse> toResponseWrapper(InventoryBalance inventoryBalance) {
        return ApiResponseWrapper.wrapUp("serviceInventoryBalance",domainToResponse(inventoryBalance));
    }
}
