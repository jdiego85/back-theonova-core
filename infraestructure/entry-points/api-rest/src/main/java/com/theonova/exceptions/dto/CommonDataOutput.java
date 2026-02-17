package com.theonova.exceptions.dto;

import com.theonova.exceptions.enums.ExceptionEnum;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.UUID;

@Data
public class CommonDataOutput extends GeneralMessage {
    public CommonDataOutput(){
        setCode("000");
        setResponse("");
        setDescription("");
        setTechnicalDetail("");
        setType(ExceptionEnum.CORRECT_TYPE.getName());
        setTransactionStatus(HttpStatus.OK.getReasonPhrase());
        setResponseDate(Instant.now());
        setTransactionCode(UUID.randomUUID().toString());
    }
}
