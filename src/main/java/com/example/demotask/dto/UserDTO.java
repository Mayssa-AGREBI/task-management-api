package com.example.demotask.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private List<TaskDTO> tasks;
}