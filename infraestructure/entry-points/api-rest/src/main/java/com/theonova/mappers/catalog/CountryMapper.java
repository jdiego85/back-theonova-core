package com.theonova.mappers.catalog;

import com.theonova.entities.catalog.Country;
import com.theonova.request.catalog.CountryRequest;
import com.theonova.response.catalog.CountryResponse;

public class CountryMapper {
    public static Country requestToDomain(CountryRequest countryRequest){
        return Country.builder()
                .name(countryRequest.getName())
                .build();
    }

    public static CountryResponse domainToResponse(Country country){
        return CountryResponse.builder()
                .name(country.name())
                .iso2(country.iso2())
                .build();
    }
}
