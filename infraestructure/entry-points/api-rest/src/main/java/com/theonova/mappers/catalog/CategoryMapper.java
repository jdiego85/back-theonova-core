package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Category;
import com.theonova.request.catalog.CategoryRequest;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.catalog.CategoryResponse;

public class CategoryMapper {

    public static Category requestToDomain(CategoryRequest categoryRequest){
        return Category.builder()
                .parentId(categoryRequest.getParentId())
                .name(categoryRequest.getName())
                .slug(categoryRequest.getSlug())
                .build();

    }

    public static CategoryResponse domainToResponse(Category category){
        return CategoryResponse.builder()
                .parentId(category.parentId())
                .name(category.name())
                .slug(category.slug())
                .build();
    }

    public static ApiResponseWrapper<CategoryResponse> domainToResponseWrapper(Category category){
        return ApiResponseWrapper.wrapUp("categoryService", domainToResponse(category));
    }

}
