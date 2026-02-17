package com.theonova.mappers;

import com.theonova.entities.catalog.Country;
import com.theonova.tables.catalog.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CountryEntityMapper {

    CountryEntityMapper INSTANCE = Mappers.getMapper(CountryEntityMapper.class);
    CountryEntity mapperDomainToEntity( Country country);
    Country mapperEntityToDomain(CountryEntity countryEntity);
}
