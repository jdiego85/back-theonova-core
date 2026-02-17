package com.theonova.service.procurement;

import java.time.Instant;

public record SupplierServiceAdapter(
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
