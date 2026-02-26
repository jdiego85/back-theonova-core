package com.theonova;

import com.theonova.entities.catalog.Country;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.CountryGateway;
import com.theonova.service.CountryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CountryUseCase {

    private final CountryGateway countryGateway;
    private final CountryService countryService;

    public Country execute(Country country) {
        Country readyToSave = countryService.flowCreateCountry(country);
        // valida que el ISO se resolvió
        if (readyToSave.iso2() == null || readyToSave.iso2().isBlank()) {
            throw new BusinessException(ErrorCode.COUNTRY_NOT_SUPPORTED);
        }
        // valida duplicado por iso2
        if (countryGateway.findByIso2(readyToSave.iso2()).isPresent()) {
            throw new BusinessException(ErrorCode.COUNTRY_ALREADY_EXISTS);
        }

        return countryGateway.saveItem(readyToSave);

    }
}
