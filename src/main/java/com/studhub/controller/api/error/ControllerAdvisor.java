package com.studhub.controller.api.error;

import com.studhub.dto.ApiError;
import com.studhub.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Класс, обрабатывающий ошибки контроллеров API.
 */
@ControllerAdvice(annotations = RestController.class)
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleBadRequestException(ApiException ex, WebRequest request) {
        HttpStatus httpStatus = ex.getClass().getAnnotation(ResponseStatus.class).value();
        return new ResponseEntity<>(new ApiError(httpStatus), httpStatus);
    }
}
