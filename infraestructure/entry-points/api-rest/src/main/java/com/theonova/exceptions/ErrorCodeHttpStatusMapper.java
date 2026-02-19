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
            case COUNTRY_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case INVALID_COUNTRY_CODE, COUNTRY_NOT_SUPPORTED -> HttpStatus.BAD_REQUEST;


            // ORDER
            case ORDER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ORDER_ALREADY_PAID -> HttpStatus.CONFLICT;

            // PAYMENT
            case PAYMENT_FAILED -> HttpStatus.BAD_REQUEST;
            case PAYMENT_TIMEOUT -> HttpStatus.GATEWAY_TIMEOUT;
        };
    }
}