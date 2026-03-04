package com.theonova.entities.checkout;

import lombok.Builder;

@Builder(toBuilder = true)
public record OrderCheckout(
    Long userId,
    Long warehouseId,
    Long orderId,
    Long productId,
    Long cart_id,
    String codeWarehouse,
    String shippingName,
    String shippingPhone,
    String shippingAddress,
    String currency
) {
}
