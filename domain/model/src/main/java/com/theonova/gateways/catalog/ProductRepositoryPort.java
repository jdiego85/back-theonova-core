package com.theonova.gateways.catalog;

import com.theonova.entities.catalog.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);

    Optional<Product> findById(long id);

    Optional<Product> findBySku(String sku);

    List<Product> findAll();

    void deleteById(long id);
}
