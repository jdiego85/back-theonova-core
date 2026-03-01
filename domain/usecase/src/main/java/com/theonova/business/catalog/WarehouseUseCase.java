package com.theonova.business.catalog;

import com.theonova.entities.catalog.Country;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.CountryGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WarehouseUseCase {

    private final WarehouseGateway warehouseGateway;
    private final CountryGateway countryGateway;

    public Warehouse execute(Warehouse warehouse){
        Country country = countryGateway.findByIso2(warehouse.iso2())
                .orElseThrow(() -> new BusinessException(ErrorCode.COUNTRY_NOT_FOUND));
        Warehouse warehouseToSave = warehouse.toBuilder()
                .countryId(country.id())
                .defaultWarehouse(true)
                .build();
        return warehouseGateway.saveItem(warehouseToSave);
    }
}
