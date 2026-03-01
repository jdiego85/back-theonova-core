package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.ProductCategory;
import com.theonova.tables.catalog.ProductCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCategoryEntityMapper {

    @Mapping(target = "id.productId", source = "productId")
    @Mapping(target = "id.categoryId", source = "categoryId")
    @Mapping(target = "product",ignore = true)
    @Mapping(target = "category",ignore = true)
    ProductCategoryEntity domainToEntity(ProductCategory productCategory);

    @Mapping(target = "productId", source = "id.productId")
    @Mapping(target = "categoryId", source = "id.categoryId")
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "sku", ignore = true)
    ProductCategory entityToDomain(ProductCategoryEntity entity);
}
