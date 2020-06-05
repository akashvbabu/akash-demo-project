package com.example.akashdemo.exceptionhandler;

import com.example.akashdemo.entity.MessageResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

/**
 * Custom Exception Handler for handling constraint violations
 */
@ControllerAdvice()
public class ConstraintViolationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleException(ConstraintViolationException e, WebRequest request) {
        String message = String.format("Invalid Input Parameter: %s", e.getMessage());
        return new ResponseEntity(new MessageResponseEntity(message), HttpStatus.BAD_REQUEST);
    }
}
