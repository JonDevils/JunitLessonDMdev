package com.example.junitlessondmdev.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value(staticConstructor = "of")
@AllArgsConstructor
public class User {
    Integer id;
    String username;
    String password;
}
