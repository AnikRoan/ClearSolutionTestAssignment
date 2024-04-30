package com.example.userstestassignment.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ExceptionDetails(
        LocalDateTime timestamp,
        String path,
        Map<String, String> errors) {

    public static ResponseEntity<ExceptionDetails> getResponseEntityError(String path,
                                                                          HttpStatus status,
                                                                          Map<String, String> errors) {

        return ResponseEntity.status(status).body(ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .path(path)
                .errors(errors)
                .build());
    }
}

