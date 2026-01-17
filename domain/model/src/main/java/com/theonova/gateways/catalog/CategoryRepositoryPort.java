package com.theonova.gateways.catalog;

import com.theonova.entities.catalog.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);

    Optional<Category> findById(long id);

    Optional<Category> findBySlug(String slug);

    List<Category> findAll();

    void deleteById(long id);
}
