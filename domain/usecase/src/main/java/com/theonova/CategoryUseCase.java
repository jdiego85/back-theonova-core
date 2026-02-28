package com.theonova;

import com.theonova.entities.catalog.Category;
import com.theonova.gateways.catalog.CategoryGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryUseCase {

    private final CategoryGateway categoryGateway;

    public Category execute(Category category) {
        return categoryGateway.saveItem(category);
    }
}
