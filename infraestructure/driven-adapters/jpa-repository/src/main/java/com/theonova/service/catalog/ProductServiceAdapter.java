package com.theonova.service.catalog;

import com.theonova.entities.catalog.Brand;
import com.theonova.entities.catalog.Product;
import com.theonova.gateways.catalog.BrandGateway;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.mappers.BrandEntityMapper;
import com.theonova.mappers.ProductEntityMapper;
import com.theonova.repository.catalog.BrandRepository;
import com.theonova.repository.catalog.ProductRepository;
import com.theonova.tables.catalog.BrandEntity;
import com.theonova.tables.catalog.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceAdapter implements ProductGateway {

    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;
    private final BrandRepository brandRepository;

    @Override
    public Optional<Product> findBySku(String sku) {
        return Optional.empty();
    }

    @Override
    public Product saveItem(Product item) {
        ProductEntity productEntity = productEntityMapper.mapperDomainToEntity(item);

        BrandEntity brandRef = brandRepository.getReferenceById(item.brandId());
        productEntity.setBrand(brandRef);

        ProductEntity productEntitySaved = productRepository.save(productEntity);
        return productEntityMapper.mapperEntityToDomain(productEntitySaved);
    }

    @Override
    public Product updateItem(Long id, Product request) {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id)
                .map(productEntityMapper::mapperEntityToDomain);
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}