package com.theonova.entities.inventory;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import lombok.Builder;

@Builder(toBuilder = true)
public record InventoryBalance(
        Long id,
        Warehouse warehouse,
        Product product,
        String skuProduct,
        String codeWarehouse,
        int onHand,
        int reserved
) {}
