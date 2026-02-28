package com.theonova.response.catalog;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.theonova.exceptions.dto.CommonDataOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseWrapper<T> extends CommonDataOutput {
    private T data;

    public static <T> ApiResponseWrapper<T> wrapUp(String service, T data) {
        ApiResponseWrapper<T> r = new ApiResponseWrapper<>();
        r.setService(service);
        r.setData(data);
        return r;
    }
}
