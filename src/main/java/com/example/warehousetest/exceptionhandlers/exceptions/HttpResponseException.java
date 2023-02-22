package com.example.warehousetest.exceptionhandlers.exceptions;

public class HttpResponseException extends RuntimeException{

    public HttpResponseException(String message) {
        super(message);
    }
}
