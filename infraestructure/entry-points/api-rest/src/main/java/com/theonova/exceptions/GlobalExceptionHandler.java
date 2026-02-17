package com.theonova.exceptions;

import com.theonova.exceptions.dto.ApiError;
import com.theonova.exceptions.dto.GeneralMessage;
import com.theonova.exceptions.enums.ExceptionEnum;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(ex.getCode(), ex.getMessage(), Instant.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GeneralMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        GeneralException generalException = new GeneralException();
        GeneralMessage menGeneralMessage = generalException.getMessages().stream().findFirst().orElse(null);
        if (menGeneralMessage == null) {
            menGeneralMessage = new GeneralMessage(UUID.randomUUID().toString(),"SERVICIO_NO_DETALLADO", Instant.now(), ExceptionEnum.TIPO_ERROR.getName(),"ERROR","ERROR","ERROR",ex.toString(),"ERROR EN EL SERVICIO");
        }
        return new ResponseEntity<>(menGeneralMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralMessage> handleAllExceptions(Exception ex) {
        GeneralException generalException = (GeneralException) ex;
        GeneralMessage menGeneralMessage = generalException.getMessages().stream().findFirst().orElse(null);
        if (menGeneralMessage == null) {
            menGeneralMessage = new GeneralMessage(UUID.randomUUID().toString(),"SERVICIO_NO_DETALLADO", Instant.now(), ExceptionEnum.TIPO_ERROR.getName(),"ERROR","ERROR","ERROR",ex.toString(),"ERROR EN EL SERVICIO");
        }
        return new ResponseEntity<>(menGeneralMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity<GeneralMessage> handleAllExceptions(NoSuchMessageException ex) {
        GeneralException generalException = new GeneralException();
        GeneralMessage menGeneralMessage = generalException.getMessages().stream().findFirst().orElse(null);
        if (menGeneralMessage == null) {
            menGeneralMessage = new GeneralMessage(UUID.randomUUID().toString(),"SERVICIO_NO_DETALLADO", Instant.now(), ExceptionEnum.TIPO_ERROR.getName(),"ERROR","ERROR","ERROR",ex.toString(),"ACTUALIZA LOS MENSAJES EN mensajes.properties");
        }
        return new ResponseEntity<>(menGeneralMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<GeneralMessage> handleAllExceptions(ClassCastException ex) {
        GeneralException generalException = new GeneralException();
        GeneralMessage menGeneralMessage = generalException.getMessages().stream().findFirst().orElse(null);
        if (menGeneralMessage == null) {
            menGeneralMessage = new GeneralMessage(UUID.randomUUID().toString(),"SERVICIO_NO_DETALLADO", Instant.now(), ExceptionEnum.TIPO_ERROR.getName(),"ERROR","ERROR","ERROR",ex.toString(),"ACTUALIZA LOS MENSAJES EN mensajes.properties");
        }
        return new ResponseEntity<>(menGeneralMessage, HttpStatus.BAD_REQUEST);
    }


}