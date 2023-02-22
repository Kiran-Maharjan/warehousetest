package com.example.warehousetest.exceptionhandlers.exceptions;

public class DataAccessException extends RuntimeException{

    public DataAccessException(String message){
        super(message);
    }
}
