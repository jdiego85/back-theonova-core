package com.theonova.business.checkout;

import com.theonova.entities.checkout.Cart;
import com.theonova.enums.CartStatus;
import com.theonova.gateways.checkout.CartGateway;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class CartUseCase {

    private static final ZoneId GUAYAQUIL_ZONE = ZoneId.of("America/Guayaquil");
    private final CartGateway cartGateway;

    public Cart execute(Cart cart) {
        Cart toSave = cart.toBuilder()
                .userId(cart.userId())
                .status(CartStatus.ACTIVE)
                .lastActivityAt(ZonedDateTime.now(GUAYAQUIL_ZONE).toInstant())
                .abandonedAt(null)
                .build();
        return cartGateway.saveItem(toSave);
    }
}
