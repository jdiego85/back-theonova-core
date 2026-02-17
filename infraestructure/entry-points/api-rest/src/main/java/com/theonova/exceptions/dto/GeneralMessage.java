package com.theonova.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralMessage {
    private String transactionCode;
    private String service;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private Instant responseDate;
    private String type;
    private String transactionStatus;
    private String code;
    private String response;
    private String description;
    @JsonIgnore
    private String technicalDetail;
}
