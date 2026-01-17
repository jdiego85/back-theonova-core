package com.theonova.entities.catalog;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductAttributeValue(
        long id,
        long productId,
        long attributeId,
        String valueString,
        BigDecimal valueNumber,
        Boolean valueBoolean,
        Instant createdAt
) {
}
