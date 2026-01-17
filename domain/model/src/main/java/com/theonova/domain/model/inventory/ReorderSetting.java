package com.theonova.domain.model.inventory;

import java.time.Instant;

public record ReorderSetting(
        long id,
        long productId,
        long warehouseId,
        int threshold,
        Instant createdAt,
        Instant updatedAt
) {
}
