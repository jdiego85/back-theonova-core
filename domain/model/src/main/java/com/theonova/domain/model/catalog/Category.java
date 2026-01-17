package com.theonova.domain.model.catalog;

import java.time.Instant;

public record Category(
        long id,
        Long parentId,
        String name,
        String slug,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}
