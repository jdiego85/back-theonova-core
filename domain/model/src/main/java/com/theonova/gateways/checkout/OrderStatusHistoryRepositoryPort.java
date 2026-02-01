package com.theonova.gateways.checkout;

import com.theonova.entities.checkout.OrderStatusHistory;
import com.theonova.gateways.ServiceManager;

import java.util.List;

public interface OrderStatusHistoryRepositoryPort extends ServiceManager<OrderStatusHistory,Long> {
    List<OrderStatusHistory> findByOrderId(long orderId);
}
