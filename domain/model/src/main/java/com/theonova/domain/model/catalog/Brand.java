package com.theonova.domain.model.catalog;

import java.time.Instant;

public record Brand(
        long id,
        String name,
        Instant createdAt
) {
}
