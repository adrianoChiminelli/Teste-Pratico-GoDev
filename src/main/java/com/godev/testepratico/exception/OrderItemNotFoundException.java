package com.godev.testepratico.exception;

import org.springframework.http.HttpStatus;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
