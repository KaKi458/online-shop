package com.onlineshop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
public class ErrorDetails {

    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;
    private final String message;

    public ErrorDetails(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }

}
