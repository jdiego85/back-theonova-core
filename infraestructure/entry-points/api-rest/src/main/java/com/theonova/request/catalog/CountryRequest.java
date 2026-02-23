package com.theonova.request.catalog;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Builder;
import lombok.Data;

@Data
public class CountryRequest extends CommonDataEntry {
    String name;
}


