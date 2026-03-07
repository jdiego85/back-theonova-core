package com.theonova.entities.checkout;

import lombok.Builder;

@Builder
public record ReserveCart (
        String skuProduct,
        String codeWarehouse,
        Long userId
){}
