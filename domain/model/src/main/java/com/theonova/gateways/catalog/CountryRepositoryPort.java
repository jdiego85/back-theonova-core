package com.theonova.gateways.catalog;

import com.theonova.entities.catalog.Country;
import com.theonova.gateways.ServiceManager;

import java.util.Optional;

public interface CountryRepositoryPort extends ServiceManager<Country,Long> {
    Optional<Country> findByIso2(String iso2);
}
