package com.theonova.response.checkout;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponse {
    private Long orderId;
    private String orderNumber;

    private String status;        // PENDING
    private String paymentStatus; // PENDING

    private BigDecimal subtotal;
    private BigDecimal shipping;
    private BigDecimal total;

    private List<OrderItemResponse> items;

    private LocalDateTime createdAt;
}
