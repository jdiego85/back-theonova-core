package com.theonova.service.checkout;

import com.theonova.entities.checkout.OrderStatusHistory;
import com.theonova.gateways.checkout.OrderStatusHistoryRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusHistoryRepositoryService implements OrderStatusHistoryRepositoryPort {
    @Override
    public List<OrderStatusHistory> findByOrderId(long orderId) {
        return List.of();
    }

    @Override
    public OrderStatusHistory saveItem(OrderStatusHistory request) {
        return null;
    }

    @Override
    public OrderStatusHistory updateItem(Long id, OrderStatusHistory request) {
        return null;
    }

    @Override
    public OrderStatusHistory findById(Long id) {
        return null;
    }

    @Override
    public List<OrderStatusHistory> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}