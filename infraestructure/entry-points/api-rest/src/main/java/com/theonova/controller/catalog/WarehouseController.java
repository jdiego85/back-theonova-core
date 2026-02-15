package com.theonova.controller.catalog;

import com.theonova.WarehouseUseCase;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.mappers.catalog.WarehouseMapper;
import com.theonova.request.catalog.WarehouseRequest;
import com.theonova.response.catalog.WarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private static WarehouseUseCase warehouseUseCase;

    @PostMapping("/create")
    public ResponseEntity<WarehouseResponse>  create (@RequestBody WarehouseRequest warehouseRequest){
        Warehouse warehousetoDomain = WarehouseMapper.requestToDomain(warehouseRequest);
        Warehouse warehouseToResponse = warehouseUseCase.execute(warehousetoDomain);
        WarehouseResponse warehouseResponse = WarehouseMapper.domainToResponse(warehouseToResponse);
        return ResponseEntity.ok(warehouseResponse);
    }
}
