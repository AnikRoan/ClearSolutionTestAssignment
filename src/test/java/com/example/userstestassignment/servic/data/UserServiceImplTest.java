package com.example.userstestassignment.servic.data;

import com.example.userstestassignment.creator.UserCreator;
import com.example.userstestassignment.entiti.User;
import com.example.userstestassignment.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String EMAIL = "5H5pU@example.com";
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserCreator userCreator;
    @Mock
    private User user;
    @Mock
    private List<User> users;

    @BeforeEach
    public void init() {
        user = User.builder()
                .email(EMAIL)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("456 Main St")
                .phoneNumber("555-555-5555")
                .build();
    }

    @Test
    void getUserByEmail() {
        when(userCreator.findByEmail(EMAIL)).thenReturn(user);

        User user = userServiceImpl.getUserByEmail(EMAIL);

        verify(userCreator, times(2)).findByEmail(EMAIL);
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    void getUserByEmailNotFound() {
        assertThrows(UserNotFoundException.class, () ->
                userServiceImpl.getUserByEmail("notFound@example.com"));
    }

    @Test
    void getAllUsers() {
        when(userCreator.findAll()).thenReturn(users);

        List<User> users = userServiceImpl.getAllUsers();

        verify(userCreator).findAll();
    }

    @Test
    void getUsersByDateBeginEnd() {
        LocalDate beginDate = LocalDate.of(1988, 1, 1);
        LocalDate endDate = LocalDate.of(2000, 1, 31);

        when(userCreator.findUsersByDateOfBirth(beginDate, endDate)).thenReturn(users);

        List<User> users = userServiceImpl.getUsersByDateBeginEnd(beginDate, endDate);

        verify(userCreator).findUsersByDateOfBirth(beginDate, endDate);
    }

    @Test
    void getUsersByDateBeginEndInvalidDate() {
        LocalDate beginDate = LocalDate.of(2000, 1, 31);
        LocalDate endDate = LocalDate.of(1988, 1, 1);

        assertThrows(UserNotFoundException.class, () ->
                userServiceImpl.getUsersByDateBeginEnd(beginDate, endDate));


    }

    @Test
    void saveUser() {
        userServiceImpl.saveUser(user);

        verify(userCreator).save(user);
    }

    @Test
    void updateUser() {
        User updateUser = User.builder()
                .email("newEmail@example.com")
                .firstName("Johnie")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("100 Main St")
                .phoneNumber("444-444-4444")
                .build();

        when(userCreator.findByEmail(EMAIL)).thenReturn(user);

        userServiceImpl.updateUser(EMAIL, updateUser);

        verify(userCreator, times(1)).findByEmail(EMAIL);


    }

    @Test
    void updateUserNotFound() {
        assertThrows(UserNotFoundException.class, () ->
                userServiceImpl.updateUser("notFound@example.com", user));
    }

    @Test
    void updateUserFirstName() {
        String newName = "Johnie";

        when(userCreator.findByEmail(EMAIL)).thenReturn(user);
        userServiceImpl.updateUserFirstName(EMAIL, newName);

        verify(userCreator, times(2)).findByEmail(EMAIL);

        assertEquals(newName, user.getFirstName());
    }

    @Test
    void updateUserLastName() {
        String newLastName = "DoDo";

        when(userCreator.findByEmail(EMAIL)).thenReturn(user);
        userServiceImpl.updateUserLastName(EMAIL, newLastName);

        verify(userCreator, times(2)).findByEmail(EMAIL);

        assertEquals(newLastName, user.getLastName());
    }

    @Test
    void updateUserDateOfBirth() {
        LocalDate newDateOfBirth = LocalDate.of(1990, 1, 1);

        when(userCreator.findByEmail(EMAIL)).thenReturn(user);
        userServiceImpl.updateUserDateOfBirth(EMAIL, newDateOfBirth);

        verify(userCreator, times(2)).findByEmail(EMAIL);

        assertEquals(newDateOfBirth, user.getDateOfBirth());
    }

    @Test
    void updateUserAddress() {
        String newAddress = "100 Main St";

        when(userCreator.findByEmail(EMAIL)).thenReturn(user);
        userServiceImpl.updateUserAddress(EMAIL, newAddress);

        verify(userCreator, times(2)).findByEmail(EMAIL);

        assertEquals(newAddress, user.getAddress());
    }

    @Test
    void updateUserPhoneNumber() {
        String newPhoneNumber = "444-444-4444";

        when(userCreator.findByEmail(EMAIL)).thenReturn(user);
        userServiceImpl.updateUserPhoneNumber(EMAIL, newPhoneNumber);

        verify(userCreator, times(2)).findByEmail(EMAIL);

        assertEquals(newPhoneNumber, user.getPhoneNumber());
    }

    @Test
    void updateUserFieldNotFound() {
        assertThrows(UserNotFoundException.class, () ->
                userServiceImpl.updateUserAddress("notFound@example.com", "100 Main St"));
    }

    @Test
    void deleteUser() {
        when(userCreator.findByEmail(EMAIL)).thenReturn(user);

        userServiceImpl.getUserByEmail(EMAIL);
        userServiceImpl.deleteUser(EMAIL);

        verify(userCreator, times(3)).findByEmail(EMAIL);
        verify(userCreator).deleteByEmail(EMAIL);
    }

    @Test
    void deleteUserInvalidEmail() {
        assertThrows(UserNotFoundException.class, () ->
                userServiceImpl.deleteUser("notFound@example.com"));
    }
}