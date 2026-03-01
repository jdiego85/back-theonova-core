package com.theonova.business.checkout;

import com.theonova.entities.checkout.Cart;
import com.theonova.enums.CartStatus;
import com.theonova.gateways.checkout.CartGateway;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class CartUseCase {

    private final CartGateway cartGateway;

    public Cart execute(Cart cart) {
        Cart toSave = cart.toBuilder()
                .userId(cart.userId())
                .status(CartStatus.ACTIVE)
                .lastActivityAt(Instant.now())
                .abandonedAt(null)
                .build();
        return cartGateway.saveItem(toSave);
    }
}
