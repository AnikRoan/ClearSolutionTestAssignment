package com.example.userstestassignment.entiti.info;

public record ValidateInfoMessage() {
    public static final String WRONG_FORMAT = "Incorrect email format";
    public static final String FIRST_NAME_REQUIREMENT = "Name cannot be empty";
    public static final String LAST_NAME_REQUIREMENT = "Last name cannot be empty";
    public static final String DATE_REQUIREMENT = "Date cannot be empty";
    public static final String EMAIL_REQUIREMENT = "Email cannot be empty";
    public static final String DATE_INFO = "Date cannot be in the future";
}
