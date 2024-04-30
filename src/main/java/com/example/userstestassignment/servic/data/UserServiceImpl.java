package com.example.userstestassignment.servic.data;

import com.example.userstestassignment.entiti.User;
import com.example.userstestassignment.exception.ExceptionMessage;
import com.example.userstestassignment.exception.UserNotFoundException;
import com.example.userstestassignment.creator.UserCreator;
import com.example.userstestassignment.roles.FieldNamesUser;
import com.example.userstestassignment.servic.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserCreator userCreator;

    @Value("${user.age.limit}")
    private int userAgeLimit;

    @Override
    public User getUserByEmail(String email) {
        if (isValidEmail(email)) {
            return userCreator.findByEmail(email);
        } else {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND, email);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userCreator.findAll();
        validateUsers(users);
        return users;
    }

    @Override
    public List<User> getUsersByDateBeginEnd(LocalDate beginDate, LocalDate endDate) {
        if (beginDate.isAfter(endDate)) {
            throw new UserNotFoundException(ExceptionMessage.INVALID_DATE, beginDate);
        }
        List<User> users = userCreator.findUsersByDateOfBirth(beginDate, endDate);
        validateUsers(users);
        return users;
    }

    private void validateUsers(List<User> users) {
        if (users.isEmpty()) {
            throw new UserNotFoundException(ExceptionMessage.NO_USERS_FOUND, "");
        }
    }

    @Override
    public User saveUser(User user) {
        validateEmail(user.getEmail());
        validateAge(user.getDateOfBirth());
        userCreator.save(user);
        return user;
    }

    private void validateAge(LocalDate dateOfBirth) {
        if(dateOfBirth.getYear()>LocalDate.now().getYear()){
            throw new UserNotFoundException(ExceptionMessage.INVALID_DATE, dateOfBirth);
        }
        if (LocalDate.now().getYear() - dateOfBirth.getYear() < userAgeLimit) {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_ADULT, dateOfBirth);
        }
    }

    private void validateEmail(String email) {
        if (userCreator.findByEmail(email) != null) {
            throw new UserNotFoundException(ExceptionMessage.USER_ALREADY_EXISTS, email);
        }
    }

    private boolean isValidEmail(String email) {
        return userCreator.findByEmail(email) != null;
    }

    @Override
    public void updateUser(String email, User updateUser) {
        if (isValidEmail(email)) {
            User user = userCreator.findByEmail(updateUser.getEmail());
            if(Objects.nonNull(user)){
                throw new UserNotFoundException(ExceptionMessage.USER_ALREADY_EXISTS, updateUser.getEmail());
            }
            userCreator.update(email, updateUser);

        } else {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND, email);
        }
    }

    @Override
    public void updateUserFirstName(String email, String firstName) {
        updateUserData(email, FieldNamesUser.FIRST_NAME, firstName);
    }

    @Override
    public void updateUserLastName(String email, String lastName) {
        updateUserData(email, FieldNamesUser.LAST_NAME, lastName);
    }

    @Override
    public void updateUserDateOfBirth(String email, LocalDate dateOfBirth) {
        validateAge(dateOfBirth);
        if(!isValidEmail(email)) {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND, email);
        }
        User user = userCreator.findByEmail(email);
        user.setDateOfBirth(dateOfBirth);

    }

    @Override
    public void updateUserAddress(String email, String address) {
        updateUserData(email, FieldNamesUser.ADDRESS, address);
    }

    @Override
    public void updateUserPhoneNumber(String email, String phoneNumber) {
        updateUserData(email, FieldNamesUser.PHONE_NUMBER, phoneNumber);
    }

    private void updateUserData(String email, FieldNamesUser fieldNamesUser, String value) {
        if (!isValidEmail(email)) {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND, email);
        }
        User user = userCreator.findByEmail(email);

        switch (fieldNamesUser) {
            case EMAIL -> user.setEmail(value);
            case FIRST_NAME -> user.setFirstName(value);
            case LAST_NAME -> user.setLastName(value);
            case ADDRESS -> user.setAddress(value);
            case PHONE_NUMBER -> user.setPhoneNumber(value);
            default -> throw new UserNotFoundException(ExceptionMessage.FIELD_NOT_FOUND, value);

        }
    }

    @Override
    public void deleteUser(String email) {
        if (isValidEmail(email)) {
            userCreator.deleteByEmail(email);
        } else {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND, email);
        }

    }
}
