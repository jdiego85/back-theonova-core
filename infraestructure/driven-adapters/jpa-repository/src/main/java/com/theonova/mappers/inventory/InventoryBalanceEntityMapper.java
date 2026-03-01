package com.theonova.mappers.inventory;

import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.tables.inventory.InventoryBalanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InventoryBalanceEntityMapper {

    InventoryBalanceEntityMapper INSTANCE = Mappers.getMapper(InventoryBalanceEntityMapper.class);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    InventoryBalanceEntity domainToEntity(InventoryBalance inventoryBalance);

    @Mapping(target = "skuProduct", source = "product.sku")
    @Mapping(target = "codeWarehouse", source = "warehouse.code")
    InventoryBalance entityToDomain(InventoryBalanceEntity inventoryBalanceEntity);
}
