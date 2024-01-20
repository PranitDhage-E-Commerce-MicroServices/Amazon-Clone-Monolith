package com.app.customExceptions;

public class UnexpectedErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnexpectedErrorException(String message) {
        super(message);
    }
}
