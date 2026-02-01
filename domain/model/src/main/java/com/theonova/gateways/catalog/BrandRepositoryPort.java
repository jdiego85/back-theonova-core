package com.theonova.gateways.catalog;

import com.theonova.entities.catalog.Brand;
import com.theonova.gateways.ServiceManager;

import java.util.Optional;

public interface BrandRepositoryPort extends ServiceManager<Brand,Long> {
    Optional<Brand> findByName(String name);

}
