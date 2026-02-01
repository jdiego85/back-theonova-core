package com.theonova.service.procurement;

import java.time.Instant;

public record SupplierRepositoryService(
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
