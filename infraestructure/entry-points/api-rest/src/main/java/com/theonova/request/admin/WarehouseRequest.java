package com.theonova.request.admin;

import com.theonova.exceptions.dto.CommonDataEntry;
import lombok.Data;

@Data
public class WarehouseRequest extends CommonDataEntry {
        String iso2;
        String code;
        String name;
        String city;
        String address;
}
