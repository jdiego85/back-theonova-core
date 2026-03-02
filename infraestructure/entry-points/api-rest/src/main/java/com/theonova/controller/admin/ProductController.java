package com.theonova.controller.admin;

import com.theonova.business.admin.ProductUseCase;
import com.theonova.entities.catalog.Product;
import com.theonova.mappers.catalog.ProductMapper;
import com.theonova.request.admin.ProductRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.admin.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;
    private final ProductMapper productMapper;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseWrapper<ProductResponse>> create(@RequestBody ProductRequest productRequest) {
        Product productToDomain = productMapper.mapperRequestToDomain(productRequest);
        Product productToResponse = productUseCase.execute(productToDomain);
        ApiResponseWrapper<ProductResponse> productResponse = productMapper.toApiResponseWrapper(productToResponse);
        return ResponseEntity.ok(productResponse);

    }
}
