package com.theonova.mappers.inventory;

import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.tables.inventory.InventoryBalanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InventoryBalanceEntityMapper {

    InventoryBalanceEntityMapper INSTANCE = Mappers.getMapper(InventoryBalanceEntityMapper.class);

    InventoryBalanceEntity domainToEntity(InventoryBalance inventoryBalance);
    InventoryBalance entityToDomain(InventoryBalanceEntity inventoryBalanceEntity);
}
