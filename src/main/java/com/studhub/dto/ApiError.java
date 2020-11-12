package com.studhub.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {
    private int statusCode;
    private String detail;

    public ApiError(HttpStatus httpStatus) {
        this.statusCode = httpStatus.value();
        this.detail = httpStatus.getReasonPhrase();
    }
}
