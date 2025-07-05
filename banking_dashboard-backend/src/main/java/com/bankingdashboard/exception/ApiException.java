package com.barclaysbanking.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final int status;

    public ApiException(String message, int status) {
        super(message);
        this.status = status;
    }
}
