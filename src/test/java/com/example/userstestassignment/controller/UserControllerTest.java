package com.example.userstestassignment.controller;

import com.example.userstestassignment.entiti.User;
import com.example.userstestassignment.servic.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static final String ROOT_PATH = "/users";
    private final String EMAIL = "6H5pU@example.com";
    private User user;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @BeforeEach
    void init() {
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
    void createUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ROOT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void getUserByEmail() throws Exception {
        when(userService.getUserByEmail(EMAIL)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_PATH + "/{email}", EMAIL))
                .andExpect(status().isOk());

        verify(userService).getUserByEmail(EMAIL);

    }

    @Test
    void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_PATH))
                .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }

    @Test
    void getUsersByDate() throws Exception {
        LocalDate beginDate = LocalDate.of(1988, 1, 1);
        LocalDate endDate = LocalDate.of(2000, 1, 1);

        when(userService.getUsersByDateBeginEnd(beginDate, endDate)).thenReturn(List.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get(ROOT_PATH +
                        "/{beginDate}/{endDate}", beginDate, endDate))
                .andExpect(status().isOk());

        verify(userService).getUsersByDateBeginEnd(beginDate, endDate);
    }

    @Test
    void updateUser() throws Exception {
        User updateUser = User.builder()
                .email("newEmail@example.com")
                .firstName("Johnie")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("100 Main St")
                .phoneNumber("444-444-4444")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_PATH + "/{email}", EMAIL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserFirstName() throws Exception {
        String firstName = "Johnie";

        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_PATH +
                                "/first-name" + "/{email}/{firstName}", EMAIL, firstName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).updateUserFirstName(EMAIL, firstName);


    }


    @Test
    void updateUserLastName() throws Exception {
        String lastName = "DoDo";

        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_PATH +
                                "/last-name" + "/{email}/{lastName}", EMAIL, lastName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).updateUserLastName(EMAIL, lastName);
    }

    @Test
    void updateUserDateOfBirth() throws Exception {
        LocalDate dateOfBirth = LocalDate.of(1999, 1, 1);

        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_PATH +
                                "/date" + "/{email}/{dateOfBirth}", EMAIL, dateOfBirth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).updateUserDateOfBirth(EMAIL, dateOfBirth);
    }

    @Test
    void updateUserAddress() throws Exception {
        String address = "1000 Main St";

        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_PATH +
                                "/address" + "/{email}/{address}", EMAIL, address)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).updateUserAddress(EMAIL, address);
    }

    @Test
    void updateUserPhoneNumber() throws Exception {
        String phoneNumber = "000-000-0000";

        mockMvc.perform(MockMvcRequestBuilders.put(ROOT_PATH +
                                "/phone" + "/{email}/{phoneNumber}", EMAIL, phoneNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).updateUserPhoneNumber(EMAIL, phoneNumber);
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT_PATH + "/{email}", EMAIL))
                .andExpect(status().isOk());

        verify(userService).deleteUser(EMAIL);
    }
}