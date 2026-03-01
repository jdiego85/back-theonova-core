package com.theonova.controller.catalog;

import com.theonova.business.catalog.BrandUseCase;
import com.theonova.entities.catalog.Brand;
import com.theonova.mappers.catalog.BrandMapper;
import com.theonova.request.catalog.BrandRequest;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.catalog.BrandResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brand/")
@RequiredArgsConstructor
public class BrandController {

    private final BrandUseCase brandUseCase;

    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<BrandResponse>> execute(@RequestBody BrandRequest brandRequest){
        Brand brandToDomain = BrandMapper.requestToDomain(brandRequest);
        Brand brandToResponse = brandUseCase.execute(brandToDomain);
        ApiResponseWrapper<BrandResponse> brandResponse = BrandMapper.domainToResponseWrapper(brandToResponse);
        return ResponseEntity.ok(brandResponse);
    }
}
