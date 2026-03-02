package com.theonova.response.admin;

import lombok.Builder;
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
