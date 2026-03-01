package com.theonova.service.checkout;

import com.theonova.entities.checkout.Order;
import com.theonova.gateways.checkout.OrderGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceAdapter implements OrderGateway {
    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return Optional.empty();
    }

    @Override
    public List<Order> findByUserId(long userId) {
        return List.of();
    }

    @Override
    public Order saveItem(Order item) {
        return null;
    }

    @Override
    public Order updateItem(Long id, Order request) {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}