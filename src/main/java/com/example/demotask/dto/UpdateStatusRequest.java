package com.example.demotask.dto;

import com.example.demotask.enums.TaskStatus;
import lombok.Data;

@Data
public class UpdateStatusRequest {
    private TaskStatus status;
}