package com.theonova.mappers.checkout;

import com.theonova.entities.checkout.ReserveCart;
import com.theonova.entities.checkout.ReservedCart;
import com.theonova.entities.checkout.ReservedItem;
import com.theonova.request.checkout.ReserveCartRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.checkout.ReserveCartResponse;
import com.theonova.response.checkout.ReservedItemResponse;
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
