package com.example.junitlessondmdev.user;

import com.example.junitlessondmdev.dto.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.*;
import java.util.function.Function;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor


public class UserService {



    private final List<User> users = new ArrayList<>();

    public  Optional<User> login(String username, String password) {
        if(username == null || password == null) {
            throw  new IllegalArgumentException("username or password / null");
        }
        return users.stream().filter(user -> user.getUsername().equals(username))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }

    public List<User> getAll() {
        return users;
    }

    public void add(User ... users) {
        this.users.addAll(Arrays.asList(users));

    }

    public Map<Long,User> getAllConvertedById() {
        return users.stream()
                .collect(toMap(User::getId, identity()));
    }
}
