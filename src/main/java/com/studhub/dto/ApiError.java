package com.studhub.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private int statusCode;
    LocalDateTime timestamp;
    String detail;

    public ApiError(HttpStatus httpStatus) {
        this.statusCode = httpStatus.value();
        this.timestamp = LocalDateTime.now();
        this.detail = httpStatus.getReasonPhrase();
    }
}
