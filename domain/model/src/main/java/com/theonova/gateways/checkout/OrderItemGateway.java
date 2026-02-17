package com.theonova.gateways.checkout;

import com.theonova.entities.checkout.OrderItem;
import com.theonova.gateways.ServiceManager;

import java.util.List;

public interface OrderItemGateway extends ServiceManager<OrderItem,Long> {
    List<OrderItem> findByOrderId(long orderId);
    List<OrderItem> findByProductId(long productId);
}
