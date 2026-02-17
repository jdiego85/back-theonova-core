package com.theonova.service.catalog;

import com.theonova.entities.catalog.Brand;
import com.theonova.gateways.catalog.BrandGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceAdapter implements BrandGateway {
    @Override
    public Optional<Brand> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Brand saveItem(Brand item) {
        return null;
    }

    @Override
    public Brand updateItem(Long id, Brand request) {
        return null;
    }

    @Override
    public Brand findById(Long id) {
        return null;
    }

    @Override
    public List<Brand> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
