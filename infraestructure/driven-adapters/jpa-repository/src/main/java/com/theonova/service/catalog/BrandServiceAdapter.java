package com.theonova.service.catalog;

import com.theonova.entities.catalog.Brand;
import com.theonova.gateways.catalog.BrandGateway;
import com.theonova.mappers.catalog.BrandEntityMapper;
import com.theonova.repository.catalog.BrandRepository;
import com.theonova.tables.catalog.BrandEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BrandServiceAdapter implements BrandGateway {

    private final BrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    @Override
    public Optional<Brand> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Brand saveItem(Brand item) {
        BrandEntity brandEntity = brandEntityMapper.mapperDomainToEntity(item);
        BrandEntity  savedBrandEntity = brandRepository.save(brandEntity);
        return brandEntityMapper.mapperBrandEntityToDomain(savedBrandEntity);
    }

    @Override
    public Brand updateItem(Long id, Brand request) {
        return null;
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id)
                .map(brandEntityMapper::mapperBrandEntityToDomain);
    }

    @Override
    public List<Brand> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
