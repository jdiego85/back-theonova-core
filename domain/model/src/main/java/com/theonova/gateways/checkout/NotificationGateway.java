package com.theonova.gateways.checkout;

import com.theonova.entities.checkout.Notification;
import com.theonova.gateways.ServiceManager;

import java.util.List;

public interface NotificationGateway extends ServiceManager<Notification,Long> {
    List<Notification> findByUserId(long userId);
}
