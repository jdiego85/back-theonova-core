package com.theonova.service.catalog;

import com.theonova.entities.catalog.ProductCategory;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.ProductCategoryGateway;
import com.theonova.mappers.catalog.ProductCategoryEntityMapper;
import com.theonova.repository.catalog.CategoryRepository;
import com.theonova.repository.catalog.ProductCategoryRepository;
import com.theonova.repository.catalog.ProductRepository;
import com.theonova.tables.catalog.CategoryEntity;
import com.theonova.tables.catalog.ProductCategoryEntity;
import com.theonova.tables.catalog.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.theonova.enums.ErrorCode.INVALID_CATEGORY_ID;
import static com.theonova.enums.ErrorCode.INVALID_PRODUCT_ID;

@RequiredArgsConstructor
@Service
public class ProductCategoryServiceAdapter implements ProductCategoryGateway {

    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryEntityMapper mapper;

    @Override
    @Transactional
    public ProductCategory saveItem(ProductCategory item) {

        ProductCategoryEntity entity = mapper.domainToEntity(item);
        ProductEntity productRef = productRepository.findById(item.productId())
                .orElseThrow(() -> new BusinessException(INVALID_PRODUCT_ID));
        CategoryEntity categoryRef = categoryRepository.findById(item.categoryId())
                .orElseThrow(() -> new BusinessException(INVALID_CATEGORY_ID));
        entity.setCategory(categoryRef);
        entity.setProduct(productRef);

        ProductCategoryEntity savedEntity = productCategoryRepository.save(entity);
        return mapper.entityToDomain(savedEntity);
    }

    @Override
    public ProductCategory updateItem(Long id, ProductCategory request) {
        return null;
    }

    @Override
    public Optional<ProductCategory> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ProductCategory> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
