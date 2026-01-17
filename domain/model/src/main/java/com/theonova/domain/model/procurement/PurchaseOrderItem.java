package com.theonova.domain.model.procurement;

import java.math.BigDecimal;
import java.time.Instant;

public record PurchaseOrderItem(
        long id,
        long purchaseOrderId,
        long productId,
        int quantityOrdered,
        int quantityReceived,
        BigDecimal unitCost,
        Instant createdAt
) {
}
