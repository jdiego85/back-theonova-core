package com.theonova.mappers.checkout;

import com.theonova.entities.checkout.OrderStatusHistory;
import com.theonova.tables.checkout.OrderStatusHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderStatusHistoryEntityMapper {

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    OrderStatusHistoryEntity domainToEntity(OrderStatusHistory orderStatusHistory);

    @Mapping(target = "orderId", source = "order.id")
    OrderStatusHistory entityToDomain(OrderStatusHistoryEntity orderStatusHistoryEntity);
}
