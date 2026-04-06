package com.example.demotask.mappers;

import com.example.demotask.dto.TaskDTO;
import com.example.demotask.dto.UserDTO;
import com.example.demotask.entities.Task;
import com.example.demotask.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class MapperTasks {

    public static TaskDTO toTaskDTO(Task task) {
        if (task == null) {
            return null;
        }

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());

        dto.setStatus(task.getStatus());

        return dto;
    }

    public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        List<TaskDTO> taskDTOs = user.getTasks() != null
                ? user.getTasks()
                .stream()
                .map(MapperTasks::toTaskDTO)
                .collect(Collectors.toList())
                : List.of();

        dto.setTasks(taskDTOs);

        return dto;
    }
}