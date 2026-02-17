package com.theonova.response.catalog;

import com.theonova.exceptions.dto.CommonDataOutput;
import lombok.Builder;

@Builder
public class CountryResponse extends CommonDataOutput {
        String iso2;
        String name;
}
