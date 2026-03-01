package com.theonova.service.catalog;

import com.theonova.entities.catalog.Country;
import com.theonova.gateways.catalog.CountryGateway;
import com.theonova.mappers.CountryEntityMapper;
import com.theonova.repository.catalog.CountryRepository;
import com.theonova.tables.catalog.CountryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceAdapter implements CountryGateway {

    private  final CountryRepository countryRepository;
    private final CountryEntityMapper  countryEntityMapper;

    @Override
    public Optional<Country> findByIso2(String iso2) {
        return countryRepository.findByIso2(iso2)
                .map(countryEntityMapper::mapperEntityToDomain);
    }

    @Override
    public Country saveItem(Country item) {
        CountryEntity countryEntity = countryEntityMapper.mapperDomainToEntity(item);
        CountryEntity countrySaved = countryRepository.save(countryEntity);
        return countryEntityMapper.mapperEntityToDomain(countrySaved);
    }

    @Override
    public Country updateItem(Long id, Country request) {
        return null;
    }

    @Override
    public Optional<Country> findById(Long id) {
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
