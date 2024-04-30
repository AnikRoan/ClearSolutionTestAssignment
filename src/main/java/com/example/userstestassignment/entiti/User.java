package com.example.userstestassignment.entiti;


import com.example.userstestassignment.entiti.info.ValidateInfoMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.example.userstestassignment.fild.JsonFieldMapper.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = ValidateInfoMessage.WRONG_FORMAT)
    @NotEmpty(message = ValidateInfoMessage.EMAIL_REQUIREMENT)
    @JsonProperty(EMAIL)
    private String email;

    @NotEmpty(message = ValidateInfoMessage.FIRST_NAME_REQUIREMENT)
    @JsonProperty(FIRST_NAME)
    private String firstName;

    @NotEmpty(message = ValidateInfoMessage.LAST_NAME_REQUIREMENT)
    @JsonProperty(LAST_NAME)
    private String lastName;

    @Past(message = ValidateInfoMessage.DATE_INFO)
    @NotNull(message = ValidateInfoMessage.DATE_REQUIREMENT)
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @JsonProperty(DATE_OF_BIRTH)
    private LocalDate dateOfBirth;

    @JsonProperty(ADDRESS)
    private String address;

    @JsonProperty(PHONE_NUMBER)
    private String phoneNumber;

}
