package com.theonova.service.catalog;

import com.theonova.entities.catalog.Warehouse;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.mappers.CountryEntityMapper;
import com.theonova.mappers.WarehouseEntityMapper;
import com.theonova.repository.catalog.CountryRepository;
import com.theonova.repository.catalog.WarehouseRepository;
import com.theonova.tables.catalog.CountryEntity;
import com.theonova.tables.catalog.WarehouseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseServiceAdapter implements WarehouseGateway {

    private final WarehouseRepository warehouseRepository;
    private final CountryRepository countryRepository;
    private final WarehouseEntityMapper warehouseEntityMapper;

    @Override
    public Optional<Warehouse> findDefaultByCountryIso2(String iso2) {
        return Optional.empty();
    }

    @Override
    public Warehouse saveItem(Warehouse warehouse) {
        WarehouseEntity warehouseEntity = warehouseEntityMapper.mapperDomainToEntity(warehouse);

        CountryEntity countryRef = countryRepository.getReferenceById(warehouse.countryId());
        warehouseEntity.setCountry(countryRef);

        WarehouseEntity savedWarehouseEntity = warehouseRepository.save(warehouseEntity);
        return warehouseEntityMapper.mapperEntityToDomain(savedWarehouseEntity);
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