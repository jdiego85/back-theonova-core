package com.theonova.service.checkout;

import com.theonova.entities.checkout.Cart;
import com.theonova.gateways.checkout.CartGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceAdapter implements CartGateway {
    @Override
    public List<Cart> findByUserId(long userId) {
        return List.of();
    }

    @Override
    public Cart saveItem(Cart item) {
        return null;
    }

    @Override
    public Cart updateItem(Long id, Cart request) {
        return null;
    }

    @Override
    public Cart findById(Long id) {
        return null;
    }

    @Override
    public List<Cart> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
