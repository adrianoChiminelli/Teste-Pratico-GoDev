package com.godev.testepratico.exception;

public class NoOrderItemFoundException extends RuntimeException {
    public NoOrderItemFoundException(String message) {
        super(message);
    }
}
