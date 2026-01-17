package com.theonova.entities.catalog;

import java.time.Instant;

public record Category(
        long id,
        Long parentId,
        String name,
        String slug,
        boolean active,//is_active
        Instant createdAt,
        Instant updatedAt
) {
}
