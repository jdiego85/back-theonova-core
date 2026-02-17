package com.theonova.gateways.checkout;

import com.theonova.entities.checkout.CartItem;
import com.theonova.gateways.ServiceManager;

import java.util.List;

public interface CartItemGateway extends ServiceManager<CartItem,Long> {
    List<CartItem> findByCartId(long cartId);
    void deleteByCartId(long cartId);
}
