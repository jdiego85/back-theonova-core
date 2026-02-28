package com.theonova.config;

import com.theonova.CategoryUseCase;
import com.theonova.CountryUseCase;
import com.theonova.ProductUseCase;
import com.theonova.WarehouseUseCase;
import com.theonova.gateways.catalog.CategoryGateway;
import com.theonova.gateways.catalog.CountryGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.repository.catalog.CategoryRepository;
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
    public CountryUseCase countryUseCase(CountryGateway countryGateway,
                                           CountryService countryService) {
        return new CountryUseCase(countryGateway, countryService);
    }

    @Bean
    public WarehouseUseCase warehouseUseCase(WarehouseGateway warehouseGateway,
                                             CountryGateway countryGateway) {
        return new WarehouseUseCase(warehouseGateway, countryGateway);
    }

    @Bean
    public CategoryUseCase categoryUseCase(CategoryGateway categoryGateway) {
        return new CategoryUseCase(categoryGateway);
    }

    @Bean
    public ProductUseCase productUseCase() {
        return new ProductUseCase();
    }


}
