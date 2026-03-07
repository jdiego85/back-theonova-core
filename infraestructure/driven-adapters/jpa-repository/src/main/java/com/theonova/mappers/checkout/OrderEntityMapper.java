package com.theonova.mappers.checkout;

import com.theonova.entities.checkout.Order;
import com.theonova.tables.checkout.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderEntityMapper {

    @Mapping(target = "warehouse", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    OrderEntity domainToEntity(Order order);

    @Mapping(target = "warehouseId", source = "warehouse.id")
    Order entityToDomain(OrderEntity orderEntity);
}
