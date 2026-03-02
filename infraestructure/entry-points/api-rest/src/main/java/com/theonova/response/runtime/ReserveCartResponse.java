package com.theonova.response.runtime;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReserveCartResponse {
    private String reservationStatus; // RESERVED, FAILED
    private List<ReservedItemResponse> reservedItems;
    private LocalDateTime expiresAt;
}
