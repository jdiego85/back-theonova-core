package com.theonova.application.port.out.checkout;

import com.theonova.domain.model.checkout.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);

    Optional<Order> findById(long id);

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByUserId(long userId);

    List<Order> findAll();
}
