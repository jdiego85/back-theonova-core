package com.theonova.application.port.out.checkout;

import com.theonova.domain.model.checkout.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartItemRepositoryPort {
    CartItem save(CartItem cartItem);

    Optional<CartItem> findById(long id);

    List<CartItem> findByCartId(long cartId);

    void deleteById(long id);

    void deleteByCartId(long cartId);
}
