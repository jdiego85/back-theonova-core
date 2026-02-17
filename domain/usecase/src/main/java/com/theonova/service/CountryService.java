package com.theonova.service;

import com.theonova.entities.catalog.Country;
import com.theonova.steps.catalog.CountryIsoStep;

public class CountryService {

    public Object flowCreateCountry(Country country) {
        String iso = CountryIsoStep.getCountryIso(country);
        country.toBuilder()
                .iso2(iso)
                .build();
        return country;
    }
}
