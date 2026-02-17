package com.theonova.repository.catalog;

import com.theonova.tables.catalog.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
}
