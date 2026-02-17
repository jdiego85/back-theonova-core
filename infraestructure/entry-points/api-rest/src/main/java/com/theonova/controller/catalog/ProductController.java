package com.theonova.controller.catalog;

import com.theonova.ProductUseCase;
import com.theonova.entities.catalog.Product;
import com.theonova.mappers.catalog.ProductMapper;
import com.theonova.request.catalog.ProductRequest;
import com.theonova.response.catalog.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;
    private final ProductMapper productMapper;

    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest productRequest) {
        Product productToDomain = productMapper.mapperRequestToDomain(productRequest);
        Product productToResponse = productUseCase.execute(productToDomain);
        ProductResponse productResponse = productMapper.mapperDomainToResponse(productToResponse);
        return ResponseEntity.ok(productResponse);

    }
}
