package com.theonova.gateways.catalog;

import com.theonova.entities.catalog.Brand;
import java.util.List;
import java.util.Optional;

public interface BrandRepositoryPort {
    Brand save(Brand brand);

    Optional<Brand> findById(long id);

    Optional<Brand> findByName(String name);

    List<Brand> findAll();

    void deleteById(long id);
}
