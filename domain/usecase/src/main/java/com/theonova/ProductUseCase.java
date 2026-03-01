package com.theonova;

import com.theonova.entities.catalog.Brand;
import com.theonova.entities.catalog.Product;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.BrandGateway;
import com.theonova.gateways.catalog.ProductGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductGateway productGateway;
    private final BrandGateway brandGateway;

    public Product execute(Product product) {
        Brand brand = brandGateway.findById(product.brandId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_BRAND_ID));
        Product productToSave = product.toBuilder()
                .brandId(brand.id())
                .build();
           return productGateway.saveItem(productToSave);
    }
}
