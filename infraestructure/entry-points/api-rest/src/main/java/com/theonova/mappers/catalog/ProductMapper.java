package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Product;
import com.theonova.request.catalog.ProductRequest;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.catalog.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product mapperRequestToDomain(ProductRequest productRequest);
    ProductResponse mapperDomainToResponse(Product product);
    default ApiResponseWrapper<ProductResponse> toApiResponseWrapper(Product product) {
        return ApiResponseWrapper.wrapUp("serviceroduct",mapperDomainToResponse(product));
    }
}
