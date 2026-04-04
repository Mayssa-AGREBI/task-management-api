package com.example.demotask.controller;

import com.example.demotask.dto.UserDTO;
import com.example.demotask.entities.User;
import com.example.demotask.services.UserService;
import com.example.demotask.mappers.MapperTasks;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Users API", description = "Operations related to user management (CRUD)")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Returns a list of all registered users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers()
                .stream()
                .map(MapperTasks::toUserDTO)
                .toList();
    }

    @Operation(summary = "Create a new user", description = "Creates a new user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO dto) {

        User user = new User(
                null,
                dto.getName(),
                dto.getEmail(),
                new ArrayList<>()
        );

        User saved = userService.addUser(user);

        return MapperTasks.toUserDTO(saved);
    }
}