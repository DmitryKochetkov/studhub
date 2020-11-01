package com.studhub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private int statusCode;
    String detail;

    public ApiError(HttpStatus httpStatus) {
        this.statusCode = httpStatus.value();
        this.detail = httpStatus.getReasonPhrase();
    }
}
