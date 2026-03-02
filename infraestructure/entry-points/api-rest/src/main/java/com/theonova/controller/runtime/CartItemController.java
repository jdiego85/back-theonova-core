package com.theonova.controller.runtime;

import com.theonova.business.runtime.CartItemUseCase;
import com.theonova.entities.checkout.CartItem;
import com.theonova.mappers.checkout.CartItemMapper;
import com.theonova.request.runtime.CartItemRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.runtime.CartItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart-items/")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemUseCase cartItemsUseCase;
    private final CartItemMapper mapper;

    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<CartItemResponse>> createCartItems(@Valid @RequestBody CartItemRequest request) {
        CartItem cartItemToDomain = mapper.requestToDomain(request);
        CartItem response = cartItemsUseCase.execute(cartItemToDomain);
        ApiResponseWrapper<CartItemResponse> responseWrapper = mapper.toResponseWrapper(response);
        return ResponseEntity.ok(responseWrapper);
    }
}
