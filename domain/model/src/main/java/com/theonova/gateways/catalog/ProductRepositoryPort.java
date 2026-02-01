package com.theonova.gateways.catalog;

import com.theonova.entities.catalog.Product;
import com.theonova.gateways.ServiceManager;

import java.util.Optional;

public interface ProductRepositoryPort extends ServiceManager<Product,Long> {
    Optional<Product> findBySku(String sku);

}
