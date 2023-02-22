package com.example.warehousetest.exceptionhandlers.exceptions;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
