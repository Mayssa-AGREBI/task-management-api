package com.example.demotask.controller;

import com.example.demotask.dto.CreateTaskRequest;
import com.example.demotask.dto.UserDTO;
import com.example.demotask.entities.User;
import com.example.demotask.services.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers()
                .stream()
                .map(com.example.demotask.mappers.MapperTasks::toUserDTO)
                .toList();
    }

    @PostMapping
    public UserDTO createUser( @RequestBody UserDTO dto) {

        User user = new User(null, dto.getName(), dto.getEmail(), new ArrayList<>());
        User saved = userService.addUser(user);

        return com.example.demotask.mappers.MapperTasks.toUserDTO(saved);
    }


}