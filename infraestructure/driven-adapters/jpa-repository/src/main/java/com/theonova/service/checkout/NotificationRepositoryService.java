package com.theonova.service.checkout;

import com.theonova.entities.checkout.Notification;
import com.theonova.gateways.checkout.NotificationRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationRepositoryService implements NotificationRepositoryPort {
    @Override
    public List<Notification> findByUserId(long userId) {
        return List.of();
    }

    @Override
    public Notification saveItem(Notification request) {
        return null;
    }

    @Override
    public Notification updateItem(Long id, Notification request) {
        return null;
    }

    @Override
    public Notification findById(Long id) {
        return null;
    }

    @Override
    public List<Notification> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
