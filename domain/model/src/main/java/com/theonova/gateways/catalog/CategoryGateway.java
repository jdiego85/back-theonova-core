package com.theonova.gateways.catalog;

import com.theonova.entities.catalog.Category;
import com.theonova.gateways.ServiceManager;

import java.util.Optional;

public interface CategoryGateway extends ServiceManager<Category,Long> {
    Optional<Category> findBySlug(String slug);
}
