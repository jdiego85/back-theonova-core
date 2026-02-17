package com.theonova.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonDataEntry {
        String TransacctionCode;
        String channel;
        String ipAddress;

}
