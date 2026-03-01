package com.theonova.entities.inventory;

import lombok.Builder;

@Builder(toBuilder = true)
public record ReorderSetting(
        Long id,
        Long productId,
        Long warehouseId,
        String skuProduct,
        String codeWarehouse,
        int threshold
) {}
