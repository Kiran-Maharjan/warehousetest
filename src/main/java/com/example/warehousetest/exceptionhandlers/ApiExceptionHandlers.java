package com.example.warehousetest.exceptionhandlers;

import com.example.warehousetest.exceptionhandlers.exceptions.*;
import com.example.warehousetest.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandlers {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<BaseResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        BaseResponse response = new BaseResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(value = {DateParserException.class})
    public ResponseEntity<BaseResponse> handleDateParserException(DateParserException exception) {
        BaseResponse response = new BaseResponse(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BaseResponse response = new BaseResponse(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(value = {HttpResponseException.class})
    public ResponseEntity<BaseResponse> handleHttpResponseException(HttpResponseException exception) {
        BaseResponse response = new BaseResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(value = {EntityAlreadyExistsException.class})
    public ResponseEntity<BaseResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        BaseResponse response = new BaseResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<BaseResponse> handleInvalidDataAccessException(DataAccessException exception) {
        BaseResponse response = new BaseResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(value = {InvalidDataException.class})
    public ResponseEntity<BaseResponse> handleInvalidDataException(InvalidDataException exception) {
        BaseResponse response = new BaseResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
