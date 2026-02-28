package com.theonova.response.catalog;

import com.theonova.exceptions.dto.CommonDataOutput;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class WarehouseResponse {
    String code;
    String name;
    String city;
    String address;
    boolean defaultWarehouse;
}
