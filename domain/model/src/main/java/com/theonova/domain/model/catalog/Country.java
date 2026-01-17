package com.theonova.domain.model.catalog;

import java.time.Instant;

public record Country(
        long id,
        String iso2,
        String name,
        Instant createdAt
) {
}
