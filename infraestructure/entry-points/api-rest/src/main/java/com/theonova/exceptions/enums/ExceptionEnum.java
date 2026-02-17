package com.theonova.exceptions.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    GENERAL_ERROR("GER-001", "General Error"),
    CORRECT_TYPE("TIE-000", "SUCCESS"),
    TIPO_INFORMACION("TIE-001", "INFO"),
    TIPO_ALVERTENCIA("TIE-002", "WARNING"),
    TIPO_ERROR("TIE-003", "ERROR");

    private final String code;
    private final String name;

    ExceptionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
