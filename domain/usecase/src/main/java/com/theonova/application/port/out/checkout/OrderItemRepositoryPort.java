package com.theonova.application.port.out.checkout;

import com.theonova.domain.model.checkout.OrderItem;
import java.util.List;
import java.util.Optional;

public interface OrderItemRepositoryPort {
    OrderItem save(OrderItem orderItem);

    Optional<OrderItem> findById(long id);

    List<OrderItem> findByOrderId(long orderId);

    List<OrderItem> findByProductId(long productId);

    void deleteById(long id);
}
