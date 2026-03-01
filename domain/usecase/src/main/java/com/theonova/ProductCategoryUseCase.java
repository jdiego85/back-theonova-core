package com.theonova;

import com.theonova.entities.catalog.Category;
import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.ProductCategory;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.CategoryGateway;
import com.theonova.gateways.catalog.ProductCategoryGateway;
import com.theonova.gateways.catalog.ProductGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductCategoryUseCase {

    private final ProductCategoryGateway gateway;
    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

    public ProductCategory execute(ProductCategory productCategory) {
        Category category = categoryGateway.findBySlug(productCategory.slug())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CATEGORY_ID));
        Product product = productGateway.findBySku(productCategory.sku())
                .orElseThrow(()-> new BusinessException(ErrorCode.INVALID_PRODUCT_ID));
        ProductCategory toSave = productCategory.toBuilder()
                .productId(product.id())
                .categoryId(category.id())
                .build();
        return gateway.saveItem(toSave);
    }
}
