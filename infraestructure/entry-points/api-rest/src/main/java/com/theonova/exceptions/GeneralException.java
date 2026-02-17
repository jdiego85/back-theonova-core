package com.theonova.exceptions;

import com.theonova.exceptions.enums.ExceptionEnum;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GeneralException extends AbstractException {
    public GeneralException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public GeneralException(Class clase, Environment environment, String... datos) {
        super(environment, clase, ExceptionEnum.GENERAL_ERROR.getCode(), HttpStatus.BAD_REQUEST, datos);
    }

    public GeneralException(Class clase,String codigo, Environment environment, String... datos) {
        super(environment, clase, codigo, HttpStatus.BAD_REQUEST, datos);
    }

    /**
     * Permite mostrar un mensaje con tipo de datos
     * @param clase clase que contiene el mensaje
     * @param environment variable de entorno
     * @param tipo ExceptionEnum.TIPO_ALVERTENCIA  ExceptionEnum.TIPO_CORRECTO ExceptionEnum.TIPO_INFORMACION ExceptionEnum.TIPO_ERROR
     * @param datos mensaje separados
     */
    public GeneralException(Class clase, Environment environment,ExceptionEnum tipo, String... datos) {
        super(environment, clase, ExceptionEnum.GENERAL_ERROR.getCode(), HttpStatus.BAD_REQUEST,tipo.getName(), datos);
    }

    public GeneralException(String message) {
        super(ExceptionEnum.GENERAL_ERROR.getCode(), message, HttpStatus.BAD_REQUEST);
    }

    public GeneralException(String servicio, String mensaje) {
        super(servicio, ExceptionEnum.GENERAL_ERROR.getCode(), mensaje, HttpStatus.BAD_REQUEST);
    }

    public GeneralException(String servicio, String mensaje, String descripcion) {
        super(servicio, ExceptionEnum.GENERAL_ERROR.getCode(), mensaje, HttpStatus.BAD_REQUEST, descripcion);
    }

    public GeneralException(ExceptionEnum code, String message) {
        super(code.getCode(), message, HttpStatus.BAD_REQUEST);
    }

    public GeneralException(ExceptionEnum code, String message, HttpStatus status) {
        super(code.getCode(), message, status);
    }
}