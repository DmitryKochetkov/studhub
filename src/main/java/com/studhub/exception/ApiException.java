package com.studhub.exception;

public abstract class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ApiException() {
        super();
    }
}
