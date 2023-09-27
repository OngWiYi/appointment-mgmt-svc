/*
 * Copyright (c) Wi Yi 2023.
 */

package com.example.appointmentmgmtsvc.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
public class BaseExceptionResponse {
    private final Timestamp timestamp;
    private final int status;
    private final String error;
    private final String message;

    public BaseExceptionResponse(HttpStatus httpStatus, String message) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
    }
}
