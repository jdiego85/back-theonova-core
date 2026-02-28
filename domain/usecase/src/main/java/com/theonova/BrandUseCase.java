package com.theonova;

import com.theonova.entities.catalog.Brand;
import com.theonova.gateways.catalog.BrandGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandUseCase {

    private final BrandGateway brandGateway;

    public  Brand execute(Brand brand){
        return brandGateway.saveItem(brand);
    }
}
