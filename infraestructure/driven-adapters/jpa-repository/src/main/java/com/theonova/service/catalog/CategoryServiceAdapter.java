package com.theonova.service.catalog;

import com.theonova.entities.catalog.Category;
import com.theonova.gateways.catalog.CategoryGateway;
import com.theonova.mappers.CategoryEntityMapper;
import com.theonova.repository.catalog.CategoryRepository;
import com.theonova.tables.catalog.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceAdapter implements CategoryGateway {

    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public Optional<Category> findBySlug(String slug) {
        return Optional.empty();
    }

    @Override
    public Category saveItem(Category item) {
        CategoryEntity categoryEntity = categoryEntityMapper.mapperDomainToEntity(item);
        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);
        return categoryEntityMapper.mapperEntityToDomain(savedCategoryEntity);
    }

    @Override
    public Category updateItem(Long id, Category request) {
        return null;
    }

    @Override
    public Optional<Category> findById(Long id) {
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
