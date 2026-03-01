package com.theonova.service.checkout;

import com.theonova.entities.checkout.OrderStatusHistory;
import com.theonova.gateways.checkout.OrderStatusHistoryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusHistoryServiceAdapter implements OrderStatusHistoryGateway {
    @Override
    public List<OrderStatusHistory> findByOrderId(long orderId) {
        return List.of();
    }

    @Override
    public OrderStatusHistory saveItem(OrderStatusHistory item) {
        return null;
    }

    @Override
    public OrderStatusHistory updateItem(Long id, OrderStatusHistory request) {
        return null;
    }

    @Override
    public Optional<OrderStatusHistory> findById(Long id) {
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