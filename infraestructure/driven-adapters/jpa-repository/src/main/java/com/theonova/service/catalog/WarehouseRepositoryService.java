package com.theonova.service.catalog;

import com.theonova.entities.catalog.Warehouse;
import com.theonova.gateways.catalog.WarehouseRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseRepositoryService implements WarehouseRepositoryPort {
    @Override
    public Optional<Warehouse> findDefaultByCountryIso2(String iso2) {
        return Optional.empty();
    }

    @Override
    public Warehouse saveItem(Warehouse request) {
        return null;
    }

    @Override
    public Warehouse updateItem(Long id, Warehouse request) {
        return null;
    }

    @Override
    public Warehouse findById(Long id) {
        return null;
    }

    @Override
    public List<Warehouse> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}