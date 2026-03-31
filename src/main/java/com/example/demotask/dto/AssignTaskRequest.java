package com.example.demotask.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignTaskRequest {

    @NotNull
    private Long taskId;

    @NotNull
    private Long userId;
}