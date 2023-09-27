/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseExceptionResponse(HttpStatus.BAD_REQUEST,
                                                                                            exception.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseExceptionResponse(HttpStatus.UNAUTHORIZED,
                                                                                             exception.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseExceptionResponse(HttpStatus.NOT_FOUND,
                                                                                          exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseExceptionResponse(HttpStatus.BAD_REQUEST,
                                                                                            exception.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new BaseExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                                             exception.getLocalizedMessage()));
    }

}
