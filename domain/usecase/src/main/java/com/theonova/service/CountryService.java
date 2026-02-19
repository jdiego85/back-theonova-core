package com.theonova.service;

import com.theonova.entities.catalog.Country;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.steps.catalog.CountryIsoStep;

public class CountryService {

    public Country flowCreateCountry(Country country) {
        if (country == null || country.name() == null || country.name().isBlank()) {
            throw new BusinessException(ErrorCode.COUNTRY_NOT_FOUND);
        }
        String iso = CountryIsoStep.getCountryIso(country);
        return country.toBuilder()
                .iso2(iso)
                .build();
    }
}
