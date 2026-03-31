package com.example.demotask.controller;

import com.example.demotask.dto.AssignTaskRequest;
import com.example.demotask.dto.CreateTaskRequest;
import com.example.demotask.dto.UpdateStatusRequest;
import com.example.demotask.entities.Task;
import com.example.demotask.entities.User;
import com.example.demotask.services.TaskService;
import com.example.demotask.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    private final UserService userService;

    public TaskController(TaskService taskService) {

        this.taskService = taskService;
        this.userService = new UserService();

    }

    @GetMapping
    public List<Task> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return taskService.getAllTasks(page, size);
    }

    @PostMapping
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.addTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,
                           @RequestBody CreateTaskRequest request) {
        return taskService.updateTask(id, request);
    }

    @PatchMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id,
                             @RequestBody UpdateStatusRequest request) {
        return taskService.updateStatus(id, request.isCompleted());
    }
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id ) {
        taskService.deleteTask(id);
        return "Task deleted successfully";
    }

    @PatchMapping("/assign")
    public Task assignTask(@Valid @RequestBody AssignTaskRequest request) {
        User user = userService.getUserById(request.getUserId());
        return taskService.assignTask(request.getTaskId(), user);
    }

}
