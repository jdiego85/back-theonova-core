package com.theonova.response.catalog;

import com.theonova.exceptions.dto.CommonDataOutput;
import lombok.Builder;

@Builder
public class WarehouseResponse extends CommonDataOutput {
        long countryId;
                                String code;
                                String name;
                                String city;
                                String address;
                                boolean active;
                                boolean defaultWarehouse;
}
