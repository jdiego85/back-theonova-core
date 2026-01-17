package com.theonova.application.port.out.catalog;

import com.theonova.domain.model.catalog.Brand;
import java.util.List;
import java.util.Optional;

public interface BrandRepositoryPort {
    Brand save(Brand brand);

    Optional<Brand> findById(long id);

    Optional<Brand> findByName(String name);

    List<Brand> findAll();

    void deleteById(long id);
}
