package com.example.demotask.mappers;

import com.example.demotask.dto.TaskDTO;
import com.example.demotask.dto.UserDTO;
import com.example.demotask.entities.Task;
import com.example.demotask.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class MapperTasks {

    public static TaskDTO toTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setCompleted(task.isCompleted());
        return dto;
    }

    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        List<TaskDTO> taskDTOs = user.getTasks()
                .stream()
                .map(com.example.demotask.mappers.MapperTasks::toTaskDTO)
                .collect(Collectors.toList());

        dto.setTasks(taskDTOs);

        return dto;
    }
}