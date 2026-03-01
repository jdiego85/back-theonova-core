package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Product;
import com.theonova.tables.catalog.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    @Mapping(target = "brand", ignore = true)
    ProductEntity mapperDomainToEntity(Product product);
    Product mapperEntityToDomain(ProductEntity productEntity);
}
