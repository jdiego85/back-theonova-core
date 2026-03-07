package com.theonova.response.checkout;

import com.theonova.enums.CartStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class CartResponse {
    private Long userId;
    private CartStatus status;
    private Instant lastActivityAt;
}
