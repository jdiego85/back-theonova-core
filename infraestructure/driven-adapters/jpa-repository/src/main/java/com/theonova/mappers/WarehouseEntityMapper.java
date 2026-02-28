package com.theonova.mappers;

import com.theonova.entities.catalog.Warehouse;
import com.theonova.tables.catalog.WarehouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WarehouseEntityMapper {

    WarehouseEntityMapper INSTANCE = Mappers.getMapper(WarehouseEntityMapper.class);

    @Mapping(target = "country", ignore = true) // clave
    WarehouseEntity mapperDomainToEntity(Warehouse warehouse);

    Warehouse mapperEntityToDomain(WarehouseEntity warehouseEntity);
}
