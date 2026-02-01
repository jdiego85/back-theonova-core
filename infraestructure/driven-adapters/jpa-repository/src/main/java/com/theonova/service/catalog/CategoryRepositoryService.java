package com.theonova.service.catalog;

import com.theonova.entities.catalog.Category;
import com.theonova.gateways.catalog.CategoryRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryRepositoryService implements CategoryRepositoryPort {
    @Override
    public Optional<Category> findBySlug(String slug) {
        return Optional.empty();
    }

    @Override
    public Category saveItem(Category request) {
        return null;
    }

    @Override
    public Category updateItem(Long id, Category request) {
        return null;
    }

    @Override
    public Category findById(Long id) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
