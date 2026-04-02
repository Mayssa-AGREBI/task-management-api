package com.example.demotask.controller;

import com.example.demotask.dto.AssignTaskRequest;
import com.example.demotask.dto.CreateTaskRequest;
import com.example.demotask.dto.TaskDTO;
import com.example.demotask.dto.UpdateStatusRequest;
import com.example.demotask.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return taskService.getAllTasks(page, size);
    }

    @PostMapping
    public TaskDTO createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.addTask(request);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id,
                              @RequestBody CreateTaskRequest request) {

        return taskService.updateTask(id, request);
    }

    @PatchMapping("/{id}/status")
    public TaskDTO updateStatus(@PathVariable Long id,
                                @RequestBody UpdateStatusRequest request) {

        return taskService.updateStatus(id, request.isCompleted());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/assign")
    public TaskDTO assignTask(@Valid @RequestBody AssignTaskRequest request) {

        return taskService.assignTask(
                request.getTaskId(),
                request.getUserId()
        );
    }

    @GetMapping("/search")
    public List<TaskDTO> search(@RequestParam String title) {
        return taskService.searchByTitle(title);
    }

    @GetMapping("/search/status")
    public List<TaskDTO> searchByStatus(@RequestParam boolean status) {
        return taskService.searchByStatus(status);
    }
}