package com.theonova.controller.inventory;

import com.theonova.business.inventory.InventoryBalanceUseCase;
import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.mappers.inventory.InventoryBalanceMapper;
import com.theonova.request.inventory.InventoryBalanceRequest;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.inventory.InventoryBalanceResponse;
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
