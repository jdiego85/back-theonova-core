package com.theonova.gateways.checkout;

import com.theonova.entities.checkout.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryPort {
    Notification save(Notification notification);

    Optional<Notification> findById(long id);

    List<Notification> findByUserId(long userId);

    List<Notification> findAll();
}
