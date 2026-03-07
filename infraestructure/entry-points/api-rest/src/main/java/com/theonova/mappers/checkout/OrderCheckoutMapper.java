package com.theonova.mappers.checkout;

import com.theonova.entities.checkout.OrderCheckout;
import com.theonova.entities.checkout.OrderCheckoutResult;
import com.theonova.entities.checkout.OrderCheckoutResultItem;
import com.theonova.request.checkout.OrderRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.checkout.OrderItemResponse;
import com.theonova.response.checkout.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderCheckoutMapper {

    OrderCheckout requestToDomain(OrderRequest request);

    @Mapping(target = "items", source = "items")
    OrderResponse domainToResponse(OrderCheckoutResult result);

    OrderItemResponse domainItemToResponse(OrderCheckoutResultItem item);

    default ApiResponseWrapper<OrderResponse> toResponseWrapper(OrderCheckoutResult result) {
        return ApiResponseWrapper.wrapUp("serviceOrderCheckout", domainToResponse(result));
    }
}
