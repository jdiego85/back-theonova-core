package com.theonova.controller.runtime;

import com.theonova.business.runtime.ReserveCartUseCase;
import com.theonova.entities.runtime.ReserveCart;
import com.theonova.entities.runtime.ReservedCart;
import com.theonova.mappers.runtime.ReserveCartMapper;
import com.theonova.request.runtime.ReserveCartRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.runtime.ReserveCartResponse;
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
