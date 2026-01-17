package com.theonova.application.port.out.checkout;

import com.theonova.domain.model.checkout.Cart;
import java.util.List;
import java.util.Optional;

public interface CartRepositoryPort {
    Cart save(Cart cart);

    Optional<Cart> findById(long id);

    List<Cart> findByUserId(long userId);

    List<Cart> findAll();

    void deleteById(long id);
}
