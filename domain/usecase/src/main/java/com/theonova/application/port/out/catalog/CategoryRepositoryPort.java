package com.theonova.application.port.out.catalog;

import com.theonova.domain.model.catalog.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);

    Optional<Category> findById(long id);

    Optional<Category> findBySlug(String slug);

    List<Category> findAll();

    void deleteById(long id);
}
