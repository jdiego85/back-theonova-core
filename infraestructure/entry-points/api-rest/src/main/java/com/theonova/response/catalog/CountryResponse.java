package com.theonova.response.catalog;

import lombok.Builder;

@Builder
public record CountryResponse (
        String iso2,
        String name
){
}
