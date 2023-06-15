package com.example.junitlessondmdev.user;

import com.example.junitlessondmdev.dto.User;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @BeforeAll
    void init () {
        System.out.println("BeforeAll: " + this);
    }

    @BeforeEach
    void prepare() {
        System.out.println("Before each: " + this);
    }

    @Test
    public void userEmptyIfNoUserAdded() {
        System.out.println("test 1 : " + this);
    var userService = new UserService();
    var users = userService.getAll();
    assertTrue(users.isEmpty(),()-> "Не нулевое значение");
    }

    @Test
    void usersSizeIfUserAdedd() {
        System.out.println("test 2 : " + this);
    var userService = new UserService();
    userService.add(new User());
    userService.add(new User());

    var users = userService.getAll();
    assertEquals(2,users.size());
    }

    @AfterEach
    void deleteDataFromDatabase() {
        System.out.println("AfterEach : " + this);
    }

    @AfterAll
    void afterDatabase() {
        System.out.println("AfterAll : " + this);
    }

}