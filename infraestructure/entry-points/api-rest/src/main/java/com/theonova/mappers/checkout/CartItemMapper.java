package com.theonova.mappers.checkout;

import com.theonova.entities.checkout.CartItem;
import com.theonova.request.checkout.CartItemRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.checkout.CartItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cartId", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "warehouseId", ignore = true)
    CartItem requestToDomain(CartItemRequest cartItemRequest);

    CartItemResponse domainToResponse(CartItem cartItem);

    default ApiResponseWrapper<CartItemResponse> toResponseWrapper(CartItem cartItem) {
        return ApiResponseWrapper.wrapUp("serviceCartItem", domainToResponse(cartItem));
    }
}
