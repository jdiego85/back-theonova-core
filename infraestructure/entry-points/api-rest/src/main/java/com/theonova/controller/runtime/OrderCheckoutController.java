package com.theonova.controller.runtime;

import com.theonova.entities.checkout.OrderCheckout;
import com.theonova.entities.runtime.ReservedCart;
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

    private final OrderUseCase orderUseCase;
    private final OrderMapper mapper;

    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<OrderResponse>> checkout(@Valid @RequestBody OrderRequest orderRequest) {
        OrderCheckout toDomain = mapper.requestToDomain(orderRequest);
        OrderCheckout response = orderUseCase.execute(toDomain);
        ApiResponseWrapper<OrderResponse> wrapper = mapper.toResponseWrapper(response);
        return ResponseEntity.ok(wrapper);
    }

}
