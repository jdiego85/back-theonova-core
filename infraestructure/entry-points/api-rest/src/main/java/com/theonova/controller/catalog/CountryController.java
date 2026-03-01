package com.theonova.controller.catalog;

import com.theonova.business.catalog.CountryUseCase;
import com.theonova.entities.catalog.Country;
import com.theonova.mappers.catalog.CountryMapper;
import com.theonova.request.catalog.CountryRequest;
import com.theonova.response.catalog.ApiResponseWrapper;
import com.theonova.response.catalog.CountryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryUseCase countryUseCase;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseWrapper<CountryResponse>> create(@RequestBody CountryRequest countryRequest){
        Country countryToDomain = CountryMapper.requestToDomain(countryRequest);
        Country countryToResponse = countryUseCase.execute(countryToDomain);
        ApiResponseWrapper<CountryResponse> countryResponse = CountryMapper.domainToResponseWrapper(countryToResponse);
    return ResponseEntity.ok(countryResponse);
    }
}
