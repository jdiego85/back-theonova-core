package com.theonova.config;

import com.theonova.business.catalog.*;
import com.theonova.business.inventory.InventoryBalanceUseCase;
import com.theonova.gateways.catalog.*;
import com.theonova.gateways.inventory.InventoryBalanceGateway;
import com.theonova.service.CountryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.theonova")
public class UseCaseConfig {

    @Bean
    public CountryService countryService() {
        return new CountryService();
    }

    @Bean
    public CountryUseCase countryUseCase(CountryGateway countryGateway, CountryService countryService) {
        return new CountryUseCase(countryGateway, countryService);
    }

    @Bean
    public WarehouseUseCase warehouseUseCase(WarehouseGateway warehouseGateway,CountryGateway countryGateway) {
        return new WarehouseUseCase(warehouseGateway, countryGateway);
    }

    @Bean
    public CategoryUseCase categoryUseCase(CategoryGateway categoryGateway) {
        return new CategoryUseCase(categoryGateway);
    }

    @Bean
    public BrandUseCase brandUseCase(BrandGateway brandGateway) {
        return  new BrandUseCase(brandGateway);
    }

    @Bean
    public ProductUseCase productUseCase(ProductGateway productGateway, BrandGateway brandGateway) {
        return new ProductUseCase(productGateway, brandGateway);
    }

    @Bean
    public ProductCategoryUseCase  productCategoryUseCase(ProductCategoryGateway productCategoryGateway,
            ProductGateway productGateway, CategoryGateway categoryGateway ) {
        return new ProductCategoryUseCase( productCategoryGateway, productGateway, categoryGateway);
    }

    @Bean
    public InventoryBalanceUseCase inventoryBalanceUseCase(InventoryBalanceGateway inventoryBalanceGateway,
        ProductGateway productGateway, WarehouseGateway warehouseGateway) {
        return new InventoryBalanceUseCase(inventoryBalanceGateway, productGateway, warehouseGateway);
    }
}
