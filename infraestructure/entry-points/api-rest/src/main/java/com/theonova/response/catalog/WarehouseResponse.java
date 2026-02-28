package com.theonova.response.catalog;

import com.theonova.exceptions.dto.CommonDataOutput;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class WarehouseResponse extends CommonDataOutput {
    String code;
    String name;
    String city;
    String address;
    boolean defaultWarehouse;
}
