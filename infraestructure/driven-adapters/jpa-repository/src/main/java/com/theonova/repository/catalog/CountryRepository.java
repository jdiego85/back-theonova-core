package com.theonova.repository.catalog;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.catalog.CountryEntity;

import java.util.Optional;

public interface CountryRepository extends RepositoryEngine<CountryEntity,Long> {
    Optional<CountryEntity> findByIso2(String iso2);
}
