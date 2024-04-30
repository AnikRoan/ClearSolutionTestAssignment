package com.example.userstestassignment.creator;

import com.example.userstestassignment.entiti.User;

import java.time.LocalDate;
import java.util.List;

public interface UserCreator{
    void save(User user);
    void update(String email, User user);
    List<User> findAll();
    void deleteByEmail(String email);
    User findByEmail(String email);
    List<User> findUsersByDateOfBirth(LocalDate begin, LocalDate end);


}
