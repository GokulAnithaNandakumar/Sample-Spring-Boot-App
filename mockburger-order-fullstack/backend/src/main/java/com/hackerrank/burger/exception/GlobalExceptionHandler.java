package com.hackerrank.burger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BurgerNameMismatchException.class)
    public ResponseEntity<String> handleBurgerMismatch(BurgerNameMismatchException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
