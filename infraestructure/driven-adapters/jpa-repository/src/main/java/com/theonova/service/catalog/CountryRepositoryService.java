package com.theonova.service.catalog;

import com.theonova.entities.catalog.Country;
import com.theonova.gateways.catalog.CountryRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryRepositoryService implements CountryRepositoryPort {
    @Override
    public Optional<Country> findByIso2(String iso2) {
        return Optional.empty();
    }

    @Override
    public Country saveItem(Country request) {
        return null;
    }

    @Override
    public Country updateItem(Long id, Country request) {
        return null;
    }

    @Override
    public Country findById(Long id) {
        return null;
    }

    @Override
    public List<Country> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
