package com.example.demotask.dto;

import com.example.demotask.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTaskRequest {

    @NotBlank(message = "Title must not be empty")
    private String title;
    private Long userId;
    private TaskStatus status;
}