package com.theonova.mappers.checkout;

import com.theonova.entities.checkout.CartItem;
import com.theonova.tables.checkout.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemEntityMapper {

    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    CartItemEntity domainToEntity(CartItem cartItem);

    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "userId", source = "cart.userId")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "warehouseId", source = "warehouse.id")
    @Mapping(target = "skuProduct", source = "product.sku")
    @Mapping(target = "codeWarehouse", source = "warehouse.code")
    CartItem entityToDomain(CartItemEntity cartItemEntity);
}
