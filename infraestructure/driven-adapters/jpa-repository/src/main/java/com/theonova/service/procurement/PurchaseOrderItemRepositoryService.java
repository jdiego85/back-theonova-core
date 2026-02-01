package com.theonova.service.procurement;

import java.math.BigDecimal;
import java.time.Instant;

public record PurchaseOrderItemRepositoryService(
        long id,
        long purchaseOrderId,
        long productId,
        int quantityOrdered,
        int quantityReceived,
        BigDecimal unitCost,
        Instant createdAt
) {
}
