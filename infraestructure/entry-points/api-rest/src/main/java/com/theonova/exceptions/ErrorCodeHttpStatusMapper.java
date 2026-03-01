package com.theonova.exceptions;

import com.theonova.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public final class ErrorCodeHttpStatusMapper {

    private ErrorCodeHttpStatusMapper() {}

    public static HttpStatus toStatus(ErrorCode code) {
        if (code == null) return HttpStatus.BAD_REQUEST;

        return switch (code) {
            // GENERAL
            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;

            // COUNTRY
            case COUNTRY_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case COUNTRY_ALREADY_EXISTS, ORDER_ALREADY_PAID -> HttpStatus.CONFLICT;
            case INVALID_COUNTRY_CODE, COUNTRY_NOT_SUPPORTED, INVALID_BRAND_ID,
                    INVALID_CATEGORY_ID, INVALID_PRODUCT_ID, INVALID_WAREHOUSE_CODE -> HttpStatus.BAD_REQUEST;

            //BRAND
            case BRAND_NOT_FOUND -> HttpStatus.NOT_FOUND;

            // ORDER
            case ORDER_NOT_FOUND -> HttpStatus.NOT_FOUND;

            // PAYMENT
            case PAYMENT_FAILED -> HttpStatus.BAD_REQUEST;
            case PAYMENT_TIMEOUT -> HttpStatus.GATEWAY_TIMEOUT;

            // Fallback for future enum values
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
