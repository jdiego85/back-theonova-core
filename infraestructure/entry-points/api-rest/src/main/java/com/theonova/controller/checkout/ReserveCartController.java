package com.theonova.controller.checkout;

import com.theonova.business.checkout.ReserveCartUseCase;
import com.theonova.entities.checkout.ReserveCart;
import com.theonova.entities.checkout.ReservedCart;
import com.theonova.mappers.checkout.ReserveCartMapper;
import com.theonova.request.checkout.ReserveCartRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.checkout.ReserveCartResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservation/")
@RequiredArgsConstructor
public class ReserveCartController {

    private final ReserveCartUseCase reserveCartUseCase;
    private final ReserveCartMapper mapper;

    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<ReserveCartResponse>> create(@Valid @RequestBody ReserveCartRequest request) {
        ReserveCart reserveCartToDomain = mapper.requestToDomain(request);
        ReservedCart response = reserveCartUseCase.execute(reserveCartToDomain);
        ApiResponseWrapper<ReserveCartResponse> responseWrapper = mapper.toResponseWrapper(response);
        return ResponseEntity.ok(responseWrapper);
    }
}
