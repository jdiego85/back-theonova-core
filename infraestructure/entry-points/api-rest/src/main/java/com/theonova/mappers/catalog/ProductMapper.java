package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Product;
import com.theonova.request.catalog.ProductRequest;
import com.theonova.response.catalog.ProductResponse;

public class ProductMapper {
    public static Product requestToDomain(ProductRequest productRequest) {
        return Product.builder()
                .sku(productRequest.sku())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .brandId(productRequest.brandId())
                .active(productRequest.active())
                .minStock(productRequest.minStock())
                .reorderPoint(productRequest.reorderPoint())
                .leadTimeDays(productRequest.leadTimeDays())
                .build();
    }

    public static ProductResponse domainToResponse(Product product) {
        return ProductResponse.builder()
                .sku(product.sku())
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .brandId(product.id())
                .active(product.active())
                .minStock(product.minStock())
                .reorderPoint(product.reorderPoint())
                .leadTimeDays(product.leadTimeDays())
                .build();
    }

}
