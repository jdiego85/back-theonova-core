package com.theonova.gateways.checkout;

import com.theonova.entities.checkout.Order;
import com.theonova.gateways.ServiceManager;

import java.util.List;
import java.util.Optional;

public interface OrderGateway extends ServiceManager<Order,Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByUserId(long userId);
}
