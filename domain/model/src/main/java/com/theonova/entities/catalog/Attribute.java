package com.theonova.entities.catalog;

import com.theonova.enums.AttributeDataType;

import java.time.Instant;

public record Attribute(
        long id,
        String name,
        AttributeDataType dataType,
        boolean filterable,//is_filterable
        Instant createdAt
) {
}
