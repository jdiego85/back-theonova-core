package com.theonova.entities.catalog;

import lombok.Builder;

import java.time.Instant;

@Builder
public record Category(
        Long id,
        Long parentId,
        String name,
        String slug
) {}
