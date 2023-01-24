package com.example.socksshipping.exception;

public class EmptyStockException extends RuntimeException {

    public EmptyStockException(String message) {
        super(message);
    }
}
