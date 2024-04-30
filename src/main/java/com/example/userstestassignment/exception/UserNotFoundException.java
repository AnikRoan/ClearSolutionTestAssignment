package com.example.userstestassignment.exception;

import static java.lang.String.format;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message, Object o) {
        super(format(message, o));

    }
}
