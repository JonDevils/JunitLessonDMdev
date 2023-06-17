package com.example.junitlessondmdev.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value(staticConstructor = "of")

public class User {
    Long id;
    String username;
    String password;
}
