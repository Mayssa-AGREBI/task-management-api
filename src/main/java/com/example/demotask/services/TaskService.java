package com.example.demotask.services;

import com.example.demotask.dto.CreateTaskRequest;
import com.example.demotask.dto.TaskDTO;
import com.example.demotask.entities.Task;
import com.example.demotask.entities.User;
import com.example.demotask.enums.TaskStatus;
import com.example.demotask.exceptions.TaskNotFoundException;
import com.example.demotask.mappers.MapperTasks;
import com.example.demotask.repositories.TaskRepository;
import com.example.demotask.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskDTO> getAllTasks(int page, int size) {
        log.info("Fetching tasks - page: {}, size: {}", page, size);

        Page<Task> tasks = taskRepository.findAll(PageRequest.of(page, size));

        return tasks.stream()
                .map(MapperTasks::toTaskDTO)
                .toList();
    }

    public TaskDTO addTask(CreateTaskRequest request) {
        log.info("Creating task with title: {}", request.getTitle());

        Task task = new Task();
        task.setTitle(request.getTitle());

        // ✅ default status
        task.setStatus(
                request.getStatus() != null ? request.getStatus() : TaskStatus.TODO
        );

        return MapperTasks.toTaskDTO(taskRepository.save(task));
    }

    public TaskDTO findTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return MapperTasks.toTaskDTO(task);
    }

    public TaskDTO updateTask(Long id, CreateTaskRequest request) {
        log.info("Updating task with id: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(request.getTitle());

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        return MapperTasks.toTaskDTO(taskRepository.save(task));
    }

    public TaskDTO updateStatus(Long id, TaskStatus status) {
        log.info("Updating task {} with status: {}", id, status);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setStatus(status);

        return MapperTasks.toTaskDTO(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);

        if (id == null) {
            throw new IllegalArgumentException("Task id must not be null");
        }

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task);
    }

    public TaskDTO assignTask(Long taskId, Long userId) {
        log.info("Assigning task {} to user {}", taskId, userId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    log.error("Task not found with id: {}", taskId);
                    return new TaskNotFoundException(taskId);
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new RuntimeException("User not found with id: " + userId);
                });

        task.setUser(user);

        log.info("Task {} successfully assigned to user {}", taskId, userId);

        return MapperTasks.toTaskDTO(taskRepository.save(task));
    }

    public List<TaskDTO> searchByTitle(String title) {
        log.info("Searching tasks with title containing: {}", title);

        return taskRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(MapperTasks::toTaskDTO)
                .toList();
    }

    public List<TaskDTO> searchByStatus(TaskStatus status) {
        log.info("Searching tasks with status: {}", status);

        return taskRepository
                .findByStatus(status)
                .stream()
                .map(MapperTasks::toTaskDTO)
                .toList();
    }
    
}