package com.example.warehousetest.exceptionhandlers.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }
}
