package com.theonova.mappers.runtime;

import com.theonova.entities.runtime.ReserveCart;
import com.theonova.entities.runtime.ReservedCart;
import com.theonova.entities.runtime.ReservedItem;
import com.theonova.request.runtime.ReserveCartRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.runtime.ReserveCartResponse;
import com.theonova.response.runtime.ReservedItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReserveCartMapper {

    @Mapping(target = "skuProduct", source = "skuProduct")
    @Mapping(target = "codeWarehouse", source = "codeWarehouse")
    @Mapping(target = "userId", source = "userId")
    ReserveCart requestToDomain(ReserveCartRequest reserveCartRequest);

    ReserveCartResponse domainToResponse(ReservedCart reservedCart);

    ReservedItemResponse reservedItemToResponse(ReservedItem reservedItem);

    default ApiResponseWrapper<ReserveCartResponse> toResponseWrapper(ReservedCart reservedCart) {
        return ApiResponseWrapper.wrapUp("serviceReserveCart", domainToResponse(reservedCart));
    }
}
