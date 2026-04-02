package com.example.demotask.controller;

import com.example.demotask.dto.UserDTO;
import com.example.demotask.entities.User;
import com.example.demotask.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User Management APIs")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers()
                .stream()
                .map(com.example.demotask.mappers.MapperTasks::toUserDTO)
                .toList();
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO dto) {

        User user = new User(
                null,
                dto.getName(),
                dto.getEmail(),
                new ArrayList<>()
        );

        User saved = userService.addUser(user);

        return com.example.demotask.mappers.MapperTasks.toUserDTO(saved);
    }
}