package com.theonova.response.admin;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountryResponse {
        String iso2;
        String name;
}
