package com.theonova.domain.model.procurement;

import java.time.Instant;

public record Supplier(
        long id,
        String name,
        String email,
        String phone,
        String taxId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}
