package com.theonova.controller.catalog;

import com.theonova.BrandUseCase;
import com.theonova.entities.catalog.Brand;
import com.theonova.mappers.catalog.BrandMapper;
import com.theonova.request.catalog.BrandRequest;
import com.theonova.response.catalog.BrandResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
public class BrandController {
    private static BrandUseCase brandUseCase;

    public ResponseEntity<BrandResponse> execute(BrandRequest brandRequest){
        Brand brandToDomain = BrandMapper.requestToDomain(brandRequest);
        Brand brandToResponse = brandUseCase.execute(brandToDomain);
        BrandResponse brandResponse = BrandMapper.domainToResponse(brandToResponse);
        return ResponseEntity.ok(brandResponse);
    }
}
