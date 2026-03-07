package com.theonova.mappers.checkout;

import com.theonova.entities.checkout.Cart;
import com.theonova.request.checkout.CartRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.checkout.CartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastActivityAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "abandonedAt", ignore = true)
    Cart requestToDomain(CartRequest cartRequest);
    CartResponse domainToResponse(Cart cart);

    default ApiResponseWrapper<CartResponse> toResponseWrapper(Cart cart) {
        return ApiResponseWrapper.wrapUp("serviceCart", domainToResponse(cart));
    }
}
