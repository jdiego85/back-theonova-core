package com.theonova.exceptions;

import com.theonova.enums.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getCode() { return errorCode.getCode(); }

    @Override
    public String getMessage() { return errorCode.getMessage(); }
}