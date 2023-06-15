package com.example.junitlessondmdev.user;

import com.example.junitlessondmdev.dto.User;

import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//Per_class - создаем один класс а не множество как с Per_metod (т.е статик можно не делать)
public class UserServiceTest { //public только в этом случае

    private UserService userService;
    private static final User IVAN =  new User(1,"ivan","123");
    private static final User PETR =  new User(2,"petr","111");
    @BeforeAll
    void init () {
        System.out.println("BeforeAll: "+ this );
    } // не статик т.к per_class

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
    @IgnoreForBinding
    void usersSizeIfUserAdedd() {
        System.out.println("test 2 : " + this);

    userService.add(IVAN);
    userService.add(PETR);

    var users = userService.getAll();
    assertEquals(2,users.size());
    }

    @Test
    void LoginSuccessIfUserExists() {
        userService.add(IVAN);
        Optional<User> user =  UserService.login(IVAN.getUsername(),IVAN.getPassword());
        assertTrue(user.isPresent());

    }

    @AfterEach
    void deleteDataFromDatabase() {
        System.out.println("AfterEach : " + this);
    }

    @AfterAll
     void afterDatabase() {
        System.out.println("AfterAll : "+ this );
    }//this можно  т.к не статик

}