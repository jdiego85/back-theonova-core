package com.theonova.exceptions;

import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.dto.GeneralMessage;
import com.theonova.exceptions.enums.ExceptionEnum;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GeneralMessage> handleBusiness(BusinessException ex) {
        HttpStatus status = ErrorCodeHttpStatusMapper.toStatus(ex.getErrorCode());
        return ResponseEntity.status(status).body(buildGeneralMessage(status, ex.getErrorCode().getCode(), ex.getMessage(), ex.getMessage(), ""));
    }

    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<GeneralMessage> handleAbstract(AbstractException ex) {
        GeneralMessage first = ex.getMessages().stream().findFirst()
                .orElseGet(() -> buildGeneralMessage(ex.status, "ERROR", "ERROR", ex.toString(), ex.getClass().getName()));
        return ResponseEntity.status(ex.status).body(first);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GeneralMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        // JSON inválido / tipos incorrectos / campos mal formateados
        GeneralMessage msg = buildGeneralMessage(
                HttpStatus.BAD_REQUEST,
                ExceptionEnum.GENERAL_ERROR.getCode(),
                "JSON inválido",
                "JSON inválido o campos con tipo incorrecto",
                ex.getClass().getName()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String description = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Request body inválido");

        GeneralMessage msg = buildGeneralMessage(
                HttpStatus.BAD_REQUEST,
                ExceptionEnum.GENERAL_ERROR.getCode(),
                "Error de validación",
                description,
                ex.getClass().getName()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    @ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity<GeneralMessage> handleNoSuchMessage(NoSuchMessageException ex) {
        GeneralMessage msg = buildGeneralMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ExceptionEnum.GENERAL_ERROR.getCode(),
                "Mensajes no configurados",
                "Actualiza los mensajes en mensajes.properties",
                ex.getClass().getName()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralMessage> handleUnexpected(Exception ex) {
        // Genérico y SEGURO. No castea.
        GeneralMessage msg = buildGeneralMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR.getCode(),
                ErrorCode.INTERNAL_ERROR.getMessage(),
                "Error inesperado en el servicio",
                ex.getClass().getName()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
    }

    private GeneralMessage buildGeneralMessage(HttpStatus status, String code, String response, String description, String technicalDetail) {
        GeneralMessage msg = new GeneralMessage();
        msg.setTransactionCode(UUID.randomUUID().toString());
        msg.setService("SERVICIO_NO_DETALLADO");
        msg.setResponseDate(Instant.now());
        msg.setType(ExceptionEnum.TIPO_ERROR.getName());
        msg.setTransactionStatus(status.getReasonPhrase());
        msg.setCode(code);
        msg.setResponse(response);
        msg.setDescription(description);
        msg.setTechnicalDetail(technicalDetail);
        return msg;
    }
}
