package com.theonova.gateways.checkout;

import com.theonova.entities.checkout.Cart;
import com.theonova.gateways.ServiceManager;

import java.util.List;
import java.util.Optional;

public interface CartGateway extends ServiceManager<Cart,Long> {
    List<Cart> findByUserId(long userId);
    Optional<Cart> lockActiveByUserId(long userId);
}
