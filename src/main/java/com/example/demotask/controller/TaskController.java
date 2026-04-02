package com.example.demotask.controller;

import com.example.demotask.dto.AssignTaskRequest;
import com.example.demotask.dto.CreateTaskRequest;
import com.example.demotask.dto.TaskDTO;
import com.example.demotask.dto.UpdateStatusRequest;
import com.example.demotask.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Task Management APIs")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get all tasks with pagination")
    @GetMapping
    public List<TaskDTO> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return taskService.getAllTasks(page, size);
    }

    @Operation(summary = "Create a new task")
    @PostMapping
    public TaskDTO createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.addTask(request);
    }

    @Operation(summary = "Update task by ID")
    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id,
                              @RequestBody CreateTaskRequest request) {

        return taskService.updateTask(id, request);
    }

    @Operation(summary = "Update task status (completed / not completed)")
    @PatchMapping("/{id}/status")
    public TaskDTO updateStatus(@PathVariable Long id,
                                @RequestBody UpdateStatusRequest request) {

        return taskService.updateStatus(id, request.isCompleted());
    }

    @Operation(summary = "Delete task by ID")
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

    @Operation(summary = "Search tasks by completion status")
    @GetMapping("/search/status")
    public List<TaskDTO> searchByStatus(@RequestParam boolean status) {
        return taskService.searchByStatus(status);
    }
}