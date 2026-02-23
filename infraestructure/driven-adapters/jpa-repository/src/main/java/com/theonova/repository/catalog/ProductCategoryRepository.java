package com.theonova.repository.catalog;

import com.theonova.tables.catalog.ProductCategoryEntity;
import com.theonova.tables.catalog.ProductCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, ProductCategoryId> {
    Optional<ProductCategoryEntity> findByIdProductIdAndIdCategoryId(Long productId, Long categoryId);
}
