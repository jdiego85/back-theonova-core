package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Brand;
import com.theonova.tables.catalog.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {

    BrandEntityMapper INSTANCE = Mappers.getMapper(BrandEntityMapper.class);

    BrandEntity mapperDomainToEntity(Brand brand);
    Brand mapperBrandEntityToDomain(BrandEntity brandEntity);

}
