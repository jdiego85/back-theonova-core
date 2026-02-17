package com.theonova.request.catalog;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Builder;

@Builder
public class CountryRequest extends CommonDataEntry {
    String name;
}


