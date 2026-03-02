package com.theonova.entities.runtime;

import lombok.Builder;

@Builder
public record ReserveCart (
        String skuProduct,
        String codeWarehouse,
        Long userId
){}
