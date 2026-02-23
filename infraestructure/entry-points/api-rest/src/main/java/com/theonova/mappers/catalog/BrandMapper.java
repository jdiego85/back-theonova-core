package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Brand;
import com.theonova.request.catalog.BrandRequest;
import com.theonova.response.catalog.BrandResponse;

public class BrandMapper {
    public static Brand requestToDomain(BrandRequest brandRequest){
        return Brand.builder()
                .name(brandRequest.getName())
                .build();
    }
    public static BrandResponse domainToResponse(Brand brand){
        return BrandResponse.builder()
                .name(brand.name())
                .build();

    }
}
