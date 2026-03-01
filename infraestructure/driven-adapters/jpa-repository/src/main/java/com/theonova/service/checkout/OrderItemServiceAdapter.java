package com.theonova.service.checkout;

import com.theonova.entities.checkout.OrderItem;
import com.theonova.gateways.checkout.OrderItemGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceAdapter implements OrderItemGateway {
    @Override
    public List<OrderItem> findByOrderId(long orderId) {
        return List.of();
    }

    @Override
    public List<OrderItem> findByProductId(long productId) {
        return List.of();
    }

    @Override
    public OrderItem saveItem(OrderItem item) {
        return null;
    }

    @Override
    public OrderItem updateItem(Long id, OrderItem request) {
        return null;
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return null;
    }

    @Override
    public List<OrderItem> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
