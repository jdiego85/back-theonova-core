package com.theonova.gateways.catalog;

import com.theonova.entities.catalog.Warehouse;
import com.theonova.gateways.ServiceManager;

import java.util.Optional;

public interface WarehouseRepositoryPort extends ServiceManager<Warehouse,Long> {
    Optional<Warehouse> findDefaultByCountryIso2(String iso2);
}
