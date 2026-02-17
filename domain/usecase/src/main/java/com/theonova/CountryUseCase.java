package com.theonova;

import com.theonova.entities.catalog.Country;
import com.theonova.gateways.catalog.CountryGateway;
import com.theonova.service.CountryService;
import com.theonova.steps.catalog.CountryIsoStep;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class CountryUseCase {

    private final CountryGateway countryGateway;
    private final CountryService countryService;

    public Country execute(Country country) {
        Country countryObject = (Country) countryService.flowCreateCountry(country);
        if (Objects.nonNull(countryObject)) {
            return countryGateway.saveItem(countryObject);
        }
        return Country.builder().build();

    }
}
