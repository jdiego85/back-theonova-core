package com.theonova.controller.checkout;

import com.theonova.business.checkout.OrderCheckoutUseCase;
import com.theonova.entities.checkout.OrderCheckout;
import com.theonova.entities.checkout.OrderCheckoutResult;
import com.theonova.mappers.checkout.OrderCheckoutMapper;
import com.theonova.request.checkout.OrderRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.checkout.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order-checkout/")
@RequiredArgsConstructor
public class OrderCheckoutController {

    private final OrderCheckoutUseCase orderCheckoutUseCase;
    private final OrderCheckoutMapper mapper;

    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<OrderResponse>> checkout(@Valid @RequestBody OrderRequest orderRequest) {
        OrderCheckout toDomain = mapper.requestToDomain(orderRequest);
        OrderCheckoutResult response = orderCheckoutUseCase.execute(toDomain);
        ApiResponseWrapper<OrderResponse> wrapper = mapper.toResponseWrapper(response);
        return ResponseEntity.ok(wrapper);
    }

}
