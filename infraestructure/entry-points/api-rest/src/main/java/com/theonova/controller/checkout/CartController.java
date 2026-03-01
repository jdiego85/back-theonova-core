package com.theonova.controller.checkout;

import com.theonova.business.checkout.CartUseCase;
import com.theonova.entities.checkout.Cart;
import com.theonova.mappers.checkout.CartMapper;
import com.theonova.request.checkout.CartRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.checkout.CartResponse;
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
