package com.example.userstestassignment.exception.handler;

import com.example.userstestassignment.exception.ExceptionDetails;
import com.example.userstestassignment.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

import static com.example.userstestassignment.exception.ExceptionDetails.getResponseEntityError;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handleValidationException(HttpServletRequest request,
                                                                      MethodArgumentNotValidException ex) {

        Map<String, String> exceptions = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        field -> field.getDefaultMessage() != null ? field.getDefaultMessage() : ""));
        return getResponseEntityError(request.getRequestURI(), HttpStatus.BAD_REQUEST, exceptions);

    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ExceptionDetails> handleNotFoundException(final HttpServletRequest request,
                                                                    final Exception ex) {
        return getResponseEntityError(request.getRequestURI(), HttpStatus.BAD_REQUEST, makeMapFromException(ex));
    }


    private Map<String, String> makeMapFromException(Exception exceptions) {
        return Map.of(exceptions.getClass().getSimpleName(), exceptions.getLocalizedMessage());
    }

}
