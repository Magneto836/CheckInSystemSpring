package com.example.punchcardsystem.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e) {
        return Result.errorGetString(400, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return Result.errorGetString(500, "Internal Server Error: " + e.getMessage());
    }
}
