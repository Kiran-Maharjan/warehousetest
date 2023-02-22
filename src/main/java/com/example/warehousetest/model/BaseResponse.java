package com.example.warehousetest.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class BaseResponse {
    private String message;
    private HttpStatus status;

    public BaseResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
    public BaseResponse() {
    }
}