package com.theonova.entities.runtime;

import lombok.Builder;

@Builder
public record ReservedItem(
        String skuProduct,
        String codeWarehouse,
        int reservedQuantity
) {}
