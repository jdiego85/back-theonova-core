package com.theonova.request.catalog;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Builder;
import lombok.Data;

@Data
public class WarehouseRequest extends CommonDataEntry {
        String iso2;
        String code;
        String name;
        String city;
        String address;
}
