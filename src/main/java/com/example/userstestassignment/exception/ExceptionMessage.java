package com.example.userstestassignment.exception;


public record ExceptionMessage() {
    public static final String USER_NOT_FOUND = "User with email [%s] not found";
    public static final String USER_ALREADY_EXISTS = "User with email [%s] already exists";
    public static final String NO_USERS_FOUND = "Users not found [%s]";
    public static final String USER_NOT_ADULT = "User is not adult [%s]";
    public static final String INVALID_DATE = "Invalid date [%s]";
    public static final String FIELD_NOT_FOUND = "Field [%s] not found";
}
