package com.theonova.entities.checkout;

import lombok.Builder;

@Builder
public record ReservedItem(
        String skuProduct,
        String codeWarehouse,
        int reservedQuantity
) {}
