package com.theonova.request.catalog;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Builder;

@Builder
public class WarehouseRequest extends CommonDataEntry {
        long countryId;
        String code;
        String name;
        String city;
        String address;
}
