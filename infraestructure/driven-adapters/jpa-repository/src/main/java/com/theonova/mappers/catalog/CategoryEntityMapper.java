package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Category;
import com.theonova.tables.catalog.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    CategoryEntityMapper INSTANCE = Mappers.getMapper(CategoryEntityMapper.class);

    CategoryEntity mapperDomainToEntity(Category domain);
    Category mapperEntityToDomain(CategoryEntity categoryEntity);

}
