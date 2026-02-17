package com.theonova.steps.catalog;

import com.theonova.entities.catalog.Country;
import com.theonova.enums.CountryIso;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;

public class CountryIsoStep {

    public static String getCountryIso(Country country) {
        if (country == null) {
            throw new BusinessException(ErrorCode.COUNTRY_NOT_FOUND);
        }
        return CountryIso.fromName(country.name());
    }
}
