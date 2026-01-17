package com.theonova.gateways.checkout;

import com.theonova.entities.checkout.OrderStatusHistory;
import java.util.List;
import java.util.Optional;

public interface OrderStatusHistoryRepositoryPort {
    OrderStatusHistory save(OrderStatusHistory orderStatusHistory);

    Optional<OrderStatusHistory> findById(long id);

    List<OrderStatusHistory> findByOrderId(long orderId);
}
