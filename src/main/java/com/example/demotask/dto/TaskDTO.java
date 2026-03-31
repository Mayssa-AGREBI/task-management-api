package com.example.demotask.dto;

import lombok.Data;

@Data
public class TaskDTO {

    private Long id;
    private String title;
    private boolean completed;
}