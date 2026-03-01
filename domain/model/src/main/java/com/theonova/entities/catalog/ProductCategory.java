package com.theonova.entities.catalog;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProductCategory(
        Long categoryId,
        Long productId,
        String slug,
        String sku
) {}
