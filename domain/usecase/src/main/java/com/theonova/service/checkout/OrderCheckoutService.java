package com.theonova.service.checkout;

import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.checkout.OrderGateway;
import com.theonova.steps.checkout.OrderCheckoutStep;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

public class OrderCheckoutService {

    private static final ZoneId GUAYAQUIL_ZONE = ZoneId.of("America/Guayaquil");
    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    public String generateUniqueOrderNumber(OrderGateway orderGateway) {
        for (int attempt = 0; attempt < 10; attempt++) {
            String orderNumber = OrderCheckoutStep.buildOrderNumber(LocalDateTime.now(GUAYAQUIL_ZONE));
            if (orderGateway.findByOrderNumber(orderNumber).isEmpty()) {
                return orderNumber;
            }
        }
        throw new BusinessException(ErrorCode.INTERNAL_ERROR);
    }

    public BigDecimal normalizeAmount(BigDecimal amount) {
        if (amount == null) {
            return ZERO;
        }
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateLineTotal(int quantity, BigDecimal unitPrice) {
        return normalizeAmount(unitPrice.multiply(BigDecimal.valueOf(quantity)));
    }

    public String normalizeCurrency(String currency) {
        if (currency == null || currency.isBlank()) {
            return "USD";
        }
        return currency.trim().toUpperCase(Locale.ROOT);
    }
}
