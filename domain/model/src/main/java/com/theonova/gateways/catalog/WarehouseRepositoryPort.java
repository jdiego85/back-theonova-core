package com.theonova.gateways.catalog;

import com.theonova.entities.catalog.Warehouse;
import java.util.List;
import java.util.Optional;

public interface WarehouseRepositoryPort {
    Warehouse save(Warehouse warehouse);

    Optional<Warehouse> findById(long id);

    Optional<Warehouse> findDefaultByCountryIso2(String iso2);

    List<Warehouse> findAll();

    void deleteById(long id);
}
