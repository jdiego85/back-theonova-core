package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.ProductCategory;
import com.theonova.request.catalog.ProductCategoryRequest;
import com.theonova.response.catalog.ApiResponseWrapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategory requestToDomain(ProductCategoryRequest productCategoryRequest);

    default ApiResponseWrapper<Void> toResponseWrapper() {
        return ApiResponseWrapper.wrapUp("serviceCategoryProduct", null);
    }

}
