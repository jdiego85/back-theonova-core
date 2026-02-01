package com.theonova.repository.catalog;

import com.theonova.tables.catalog.ProductTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductTable,Long> {
}
