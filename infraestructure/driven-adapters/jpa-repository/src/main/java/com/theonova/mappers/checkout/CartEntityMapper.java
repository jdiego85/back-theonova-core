package com.theonova.mappers.checkout;

import com.theonova.entities.checkout.Cart;
import com.theonova.tables.checkout.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface CartEntityMapper {

    ZoneId GUAYAQUIL_ZONE = ZoneId.of("America/Guayaquil");

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    CartEntity domainToEntity(Cart cart);
    Cart entityToDomain(CartEntity cartEntity);

    default LocalDateTime map(Instant value) {
        return value == null ? null : LocalDateTime.ofInstant(value, GUAYAQUIL_ZONE);
    }

    default Instant map(LocalDateTime value) {
        return value == null ? null : value.atZone(GUAYAQUIL_ZONE).toInstant();
    }
}
