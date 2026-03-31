package com.example.demotask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class CreateUserRequest {


        @NotBlank(message = "Name is required")
        private String name;

        @Email(message = "Email invalid")
        @NotBlank(message = "Email is required")
        private String email;

        // getters setters
    }

