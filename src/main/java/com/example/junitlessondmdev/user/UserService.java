package com.example.junitlessondmdev.user;

import com.example.junitlessondmdev.dto.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final List<User> users = new ArrayList<>();

    public static Optional<User> login(String username, String password) {
    }

    public List<User> getAll() {
        return users;
    }

    public boolean add(User user) {
        return users.add(user);

    }
}
