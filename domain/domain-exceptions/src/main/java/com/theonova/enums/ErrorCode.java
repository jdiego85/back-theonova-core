package com.theonova.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // GENERAL
    INTERNAL_ERROR("GEN-001", "Internal server error"),

    // COUNTRY
    COUNTRY_NOT_FOUND("CTR-001", "Country not found"),
    COUNTRY_ALREADY_EXISTS("CTR-002", "Country already exists"),
    INVALID_COUNTRY_CODE("CTR-003", "Invalid country ISO code"),
    COUNTRY_NOT_SUPPORTED("CTR-004","País no soportado"),

    //CATEGORY
    INVALID_CATEGORY_ID("CAT-001", "Invalid category id"),

    //PRODUCT
    INVALID_PRODUCT_ID("PRD-001", "Invalid product id"),

    //BRAND
    BRAND_NOT_FOUND("BRD-001", "Brand not found"),
    INVALID_BRAND_ID("BRD-002", "Invalid brand ID"),

    //WAREHOUSE
    INVALID_WAREHOUSE_CODE("WRH-001", "Invalid warehouse code"),

    // ORDER
    ORDER_NOT_FOUND("ORD-001", "Order not found"),
    ORDER_ALREADY_PAID("ORD-002", "Order already paid"),

    // PAYMENT
    PAYMENT_FAILED("PAY-001", "Payment failed"),
    PAYMENT_TIMEOUT("PAY-002", "Payment timeout");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
