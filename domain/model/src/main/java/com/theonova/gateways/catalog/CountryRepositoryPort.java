package com.theonova.gateways.catalog;

import com.theonova.entities.catalog.Country;
import java.util.List;
import java.util.Optional;

public interface CountryRepositoryPort {
    Country save(Country country);

    Optional<Country> findById(long id);

    Optional<Country> findByIso2(String iso2);

    List<Country> findAll();

    void deleteById(long id);
}
