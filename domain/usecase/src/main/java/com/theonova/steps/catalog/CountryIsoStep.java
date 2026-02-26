package com.theonova.steps.catalog;

import com.theonova.entities.catalog.Country;
import com.theonova.CountryIso;

public class CountryIsoStep {

    public static String getCountryIso(Country country) {
        if (country == null || country.name() == null) return null;
        return CountryIso.fromName(country.name());
    }
}
