package com.example.userstestassignment.servic;

import com.example.userstestassignment.entiti.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getUsersByDateBeginEnd(LocalDate beginDate, LocalDate endDate);

    User saveUser(User user);

    void updateUser(String email, User user);

    void updateUserFirstName(String email, String firstName);

    void updateUserLastName(String email, String lastName);

    void updateUserDateOfBirth(String email, LocalDate dateOfBirth);

    void updateUserAddress(String email, String address);

    void updateUserPhoneNumber(String email, String phoneNumber);

    void deleteUser(String email);

}
