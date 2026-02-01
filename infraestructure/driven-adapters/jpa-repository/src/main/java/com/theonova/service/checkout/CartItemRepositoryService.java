package com.theonova.service.checkout;

import com.theonova.entities.checkout.CartItem;
import com.theonova.gateways.checkout.CartItemRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemRepositoryService implements CartItemRepositoryPort {
    @Override
    public List<CartItem> findByCartId(long cartId) {
        return List.of();
    }

    @Override
    public void deleteByCartId(long cartId) {

    }

    @Override
    public CartItem saveItem(CartItem request) {
        return null;
    }

    @Override
    public CartItem updateItem(Long id, CartItem request) {
        return null;
    }

    @Override
    public CartItem findById(Long id) {
        return null;
    }

    @Override
    public List<CartItem> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}