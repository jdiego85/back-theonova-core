package com.theonova.controller.catalog;

import com.theonova.CountryUseCase;
import com.theonova.entities.catalog.Country;
import com.theonova.mappers.catalog.CountryMapper;
import com.theonova.request.catalog.CountryRequest;
import com.theonova.response.catalog.CountryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
public class CountryController {

    private static CountryUseCase countryUseCase;

    @PostMapping("/create")
    public ResponseEntity<CountryResponse> create(@RequestBody CountryRequest countryRequest){
        Country countryToDomain = CountryMapper.requestToDomain(countryRequest);
        Country countryToResponse = countryUseCase.execute(countryToDomain);
        CountryResponse countryResponse = CountryMapper.domainToResponse(countryToResponse);
    return ResponseEntity.ok(countryResponse);
    }
}
