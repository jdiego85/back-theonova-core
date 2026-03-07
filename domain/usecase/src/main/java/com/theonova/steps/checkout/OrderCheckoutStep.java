package com.theonova.steps.checkout;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class OrderCheckoutStep {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter ORDER_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private OrderCheckoutStep() {}

    public static String buildOrderNumber(LocalDateTime dateTime) {
        int suffix = RANDOM.nextInt(100_000);
        return "EC-" + dateTime.format(ORDER_DATE_FORMAT) + "-" + String.format("%05d", suffix);
    }
}
