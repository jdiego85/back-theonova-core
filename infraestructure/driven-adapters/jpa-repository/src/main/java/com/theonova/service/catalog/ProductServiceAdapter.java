package com.theonova.service.catalog;

import com.theonova.entities.catalog.Product;
import com.theonova.gateways.catalog.ProductGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceAdapter implements ProductGateway {
    @Override
    public Optional<Product> findBySku(String sku) {
        return Optional.empty();
    }

    @Override
    public Product saveItem(Product item) {
        return null;
    }

    @Override
    public Product updateItem(Long id, Product request) {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}