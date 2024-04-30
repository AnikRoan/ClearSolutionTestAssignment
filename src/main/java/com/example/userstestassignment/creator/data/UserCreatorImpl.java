package com.example.userstestassignment.creator.data;

import com.example.userstestassignment.entiti.User;
import com.example.userstestassignment.creator.UserCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class UserCreatorImpl implements UserCreator {
    private final String JSON_FILE_PATH = "src/main/resources/users.json";
    private final ObjectMapper objectMapper = new JsonMapper().registerModule(new JavaTimeModule());
    private final File file = new File(JSON_FILE_PATH);
    private final List<User> users;

    public UserCreatorImpl() {
        this.users = deSerialization(file);
    }

    private List<User> deSerialization(File file) {
        if (file.exists()) {
            try (FileInputStream fileReader = new FileInputStream(file)) {
                return new ArrayList<>(Arrays.asList(objectMapper.readValue(fileReader, User[].class)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(String email, User user) {
        for (User u : users) {

            if (u.getEmail().equals(email)) {
                updateField(user, u);
            }
        }
    }

    private void updateField(User user, User u) {

        u.setEmail(user.getEmail());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setDateOfBirth(user.getDateOfBirth());
        if (Objects.nonNull(user.getAddress()))
            u.setAddress(user.getAddress());
        if (Objects.nonNull(user.getPhoneNumber()))
            u.setPhoneNumber(user.getPhoneNumber());


    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public void deleteByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                users.remove(user);
                break;
            }
        }
    }

    @Override
    public User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findUsersByDateOfBirth(LocalDate begin, LocalDate end) {
        List<User> usersDate = new ArrayList<>();
        for (User user : users) {
            if (user.getDateOfBirth().isAfter(begin) && user.getDateOfBirth().isBefore(end)) {
                usersDate.add(user);
            }
        }
        return usersDate;

    }

    @PreDestroy
    private void serializable() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            objectMapper.writeValue(file, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
