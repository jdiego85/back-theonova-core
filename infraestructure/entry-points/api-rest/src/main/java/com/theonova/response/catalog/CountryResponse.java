package com.theonova.response.catalog;

import com.theonova.exceptions.dto.CommonDataOutput;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class CountryResponse {
        String iso2;
        String name;
}
