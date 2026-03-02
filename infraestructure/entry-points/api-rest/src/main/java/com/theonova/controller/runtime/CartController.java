package com.theonova.controller.runtime;

import com.theonova.business.runtime.CartUseCase;
import com.theonova.entities.checkout.Cart;
import com.theonova.mappers.checkout.CartMapper;
import com.theonova.request.runtime.CartRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.runtime.CartResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart/")
@RequiredArgsConstructor
public class CartController {

    private final CartUseCase cartUseCase;
    private final CartMapper mapper;

    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<CartResponse>> createCart(@Valid @RequestBody CartRequest request) {
        Cart cartToDomain = mapper.requestToDomain(request);
        Cart response = cartUseCase.execute(cartToDomain);
        ApiResponseWrapper<CartResponse> responseWrapper = mapper.toResponseWrapper(response);
        return ResponseEntity.ok(responseWrapper);
    }
}
