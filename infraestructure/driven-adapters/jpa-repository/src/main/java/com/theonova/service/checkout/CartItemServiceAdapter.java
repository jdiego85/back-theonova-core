package com.theonova.service.checkout;

import com.theonova.entities.checkout.CartItem;
import com.theonova.gateways.checkout.CartItemGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceAdapter implements CartItemGateway {
    @Override
    public List<CartItem> findByCartId(long cartId) {
        return List.of();
    }

    @Override
    public void deleteByCartId(long cartId) {

    }

    @Override
    public CartItem saveItem(CartItem item) {
        return null;
    }

    @Override
    public CartItem updateItem(Long id, CartItem request) {
        return null;
    }

    @Override
    public Optional<CartItem> findById(Long id) {
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