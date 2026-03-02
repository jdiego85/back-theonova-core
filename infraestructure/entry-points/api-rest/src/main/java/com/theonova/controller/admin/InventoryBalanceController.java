package com.theonova.controller.admin;

import com.theonova.business.admin.InventoryBalanceUseCase;
import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.mappers.inventory.InventoryBalanceMapper;
import com.theonova.request.admin.InventoryBalanceRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.admin.InventoryBalanceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory-balance/")
@RequiredArgsConstructor
public class InventoryBalanceController {

    private final InventoryBalanceUseCase  inventoryBalanceUseCase;
    private final InventoryBalanceMapper mapper;

    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<InventoryBalanceResponse>> create(@Valid @RequestBody InventoryBalanceRequest request) {
        InventoryBalance inventoryBalanceToDomain = mapper.requestToDomain(request);
        InventoryBalance response = inventoryBalanceUseCase.execute(inventoryBalanceToDomain);
        ApiResponseWrapper<InventoryBalanceResponse> responseWrapper = mapper.toResponseWrapper(response);
        return ResponseEntity.ok(responseWrapper);
    }
}
