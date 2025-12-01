package com.cart.ecom_proj.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandeler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,Object>> productNotFoundException(ProductNotFoundException ex){
    HashMap<String,Object> errorResponse = new HashMap<>();
    errorResponse.put("message",ex.getMessage());
    errorResponse.put("status",false);
    errorResponse.put("timeStamp",LocalDateTime.now());
    return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
    

}
