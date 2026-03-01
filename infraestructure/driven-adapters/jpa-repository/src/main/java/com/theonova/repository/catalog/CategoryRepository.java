package com.theonova.repository.catalog;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.catalog.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends RepositoryEngine<CategoryEntity,Long> {
    Optional<CategoryEntity> findBySlug(String slug);
}
