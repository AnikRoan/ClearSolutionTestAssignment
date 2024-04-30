package com.example.userstestassignment.controller;


import com.example.userstestassignment.entiti.User;
import com.example.userstestassignment.servic.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.example.userstestassignment.fild.JsonFieldMapper.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{beginDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersByDate(@PathVariable
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
                                     @PathVariable
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return userService.getUsersByDateBeginEnd(beginDate, endDate);
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable String email,
                          @Valid @RequestBody User user) {
        userService.updateUser(email, user);
        return userService.getUserByEmail(user.getEmail());
    }

    @PutMapping("/first-name/{email}/{firstName}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUserFirstName(@PathVariable String email,
                                    @Valid @PathVariable(FIRST_NAME) String firstName) {
        userService.updateUserFirstName(email, firstName);
        return userService.getUserByEmail(email);
    }

    @PutMapping("/last-name/{email}/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUserLastName(@PathVariable String email,
                                   @Valid @PathVariable(LAST_NAME) String lastName) {
        userService.updateUserLastName(email, lastName);
        return userService.getUserByEmail(email);
    }

    @PutMapping("/date/{email}/{dateOfBirth}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUserDateOfBirth(@PathVariable String email,
                                      @Valid @PathVariable(DATE_OF_BIRTH)  LocalDate dateOfBirth) {
        userService.updateUserDateOfBirth(email, dateOfBirth);
        return userService.getUserByEmail(email);
    }

    @PutMapping("/address/{email}/{address}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUserAddress(@PathVariable String email,
                                  @PathVariable(ADDRESS) String address) {
        userService.updateUserAddress(email, address);
        return userService.getUserByEmail(email);
    }

    @PutMapping("/phone/{email}/{phoneNumber}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUserPhoneNumber(@PathVariable String email,
                                      @PathVariable(PHONE_NUMBER) String phoneNumber) {
        userService.updateUserPhoneNumber(email, phoneNumber);
        return userService.getUserByEmail(email);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable  String email) {
        userService.deleteUser(email);
    }

}
