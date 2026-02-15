package com.theonova.entities.catalog;

import lombok.Builder;

import java.time.Instant;

@Builder
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
