package com.theonova.mappers.inventory;

import com.theonova.entities.inventory.ReorderSetting;
import com.theonova.tables.inventory.ReorderSettingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReorderSettingEntityMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    ReorderSettingEntity domainToEntity(ReorderSetting reorderSetting);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "warehouseId", source = "warehouse.id")
    @Mapping(target = "skuProduct", source = "product.sku")
    @Mapping(target = "codeWarehouse", source = "warehouse.code")
    ReorderSetting entityToDomain(ReorderSettingEntity reorderSettingEntity);
}
