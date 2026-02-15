package com.theonova.entities.catalog;

import lombok.Builder;

import java.time.Instant;

@Builder
public record Country(
        long id,
        String iso2,
        String name,
        Instant createdAt
) {
}
