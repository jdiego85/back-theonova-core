package com.theonova.steps.catalog;

import com.theonova.entities.catalog.Country;
import com.theonova.enums.CountryIso;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;

import java.text.Normalizer;
import java.util.Locale;

public class CountryIsoStep {

    public static String getCountryIso(Country country) {
        if (country == null || country.name() == null) return null;
        return CountryIso.fromName(country.name());
    }
}
