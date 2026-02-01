package com.theonova.gateways.checkout;

import com.theonova.entities.checkout.Cart;
import com.theonova.gateways.ServiceManager;

import java.util.List;

public interface CartRepositoryPort extends ServiceManager<Cart,Long> {
    List<Cart> findByUserId(long userId);
}
