package com.example.demotask.dto;

import com.example.demotask.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskDTO {

    private Long id;
    private String title;
    private TaskStatus status;
}