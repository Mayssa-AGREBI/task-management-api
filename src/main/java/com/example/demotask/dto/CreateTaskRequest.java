package com.example.demotask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTaskRequest {

    @NotBlank(message = "Title must not be empty")
    private String title;

    private boolean completed;
}