package com.theonova.controller.admin;

import com.theonova.entities.catalog.ProductCategory;
import com.theonova.mappers.catalog.ProductCategoryMapper;
import com.theonova.request.admin.ProductCategoryRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.business.admin.ProductCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-category/")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryUseCase useCase;
    private final ProductCategoryMapper mapper;

    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<Void>> create(@RequestBody ProductCategoryRequest request){
        ProductCategory toDomain = mapper.requestToDomain(request);
        useCase.execute(toDomain);
        ApiResponseWrapper<Void> response = mapper.toResponseWrapper();
        return ResponseEntity.ok(response);
    }
}
