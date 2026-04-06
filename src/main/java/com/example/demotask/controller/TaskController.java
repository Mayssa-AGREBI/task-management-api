package com.example.demotask.controller;

import com.example.demotask.dto.AssignTaskRequest;
import com.example.demotask.dto.CreateTaskRequest;
import com.example.demotask.dto.TaskDTO;
import com.example.demotask.dto.UpdateStatusRequest;
import com.example.demotask.enums.TaskStatus;
import com.example.demotask.services.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tasks API", description = "Task management operations (CRUD, assign, search, status update)")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get paginated tasks", description = "Returns list of tasks with pagination")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping
    public List<TaskDTO> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return taskService.getAllTasks(page, size);
    }

    @Operation(summary = "Create a new task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public TaskDTO createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.addTask(request);
    }

    @Operation(summary = "Update a task by ID")
    @PutMapping("/{id}")
    public TaskDTO updateTask(
            @PathVariable Long id,
            @RequestBody CreateTaskRequest request) {

        return taskService.updateTask(id, request);
    }

    @Operation(summary = "Update task status (TODO / DOING / DONE)")
    @PatchMapping("/{id}/status")
    public TaskDTO updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request) {

        return taskService.updateStatus(id, request.getStatus());
    }

    @Operation(summary = "Delete a task by ID")
    @ApiResponse(responseCode = "204", description = "Task deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign task to a user")
    @PatchMapping("/assign")
    public TaskDTO assignTask(@Valid @RequestBody AssignTaskRequest request) {

        return taskService.assignTask(
                request.getTaskId(),
                request.getUserId()
        );
    }

    @Operation(summary = "Search tasks by title")
    @GetMapping("/search")
    public List<TaskDTO> search(@RequestParam String title) {
        return taskService.searchByTitle(title);
    }

    @Operation(summary = "Search tasks by status")
    @GetMapping("/search/status")
    public List<TaskDTO> searchByStatus(@RequestParam TaskStatus status) {
        return taskService.searchByStatus(status);
    }
}