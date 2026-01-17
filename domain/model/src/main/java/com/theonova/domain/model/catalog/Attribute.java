package com.theonova.domain.model.catalog;

import java.time.Instant;

public record Attribute(
        long id,
        String name,
        AttributeDataType dataType,
        boolean filterable,
        Instant createdAt
) {
}
