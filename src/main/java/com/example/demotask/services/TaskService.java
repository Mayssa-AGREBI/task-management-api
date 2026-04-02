package com.example.demotask.services;

import com.example.demotask.dto.CreateTaskRequest;
import com.example.demotask.dto.TaskDTO;
import com.example.demotask.entities.Task;
import com.example.demotask.entities.User;
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

        Page<Task> tasks = taskRepository.findAll(PageRequest.of(page, size));

        return tasks.stream()
                .map(MapperTasks::toTaskDTO)
                .toList();
    }

    public TaskDTO addTask(CreateTaskRequest request) {
        log.info("Creating task with title: {}", request.getTitle());
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setCompleted(request.isCompleted());
        return MapperTasks.toTaskDTO(taskRepository.save(task));
    }

    public TaskDTO findTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        return MapperTasks.toTaskDTO(task);
    }

    public TaskDTO updateTask(Long id, CreateTaskRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(request.getTitle());
        task.setCompleted(request.isCompleted());

        return MapperTasks.toTaskDTO(taskRepository.save(task));
    }

    public TaskDTO updateStatus(Long id, boolean completed) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setCompleted(completed);

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

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);

        return MapperTasks.toTaskDTO(taskRepository.save(task));
    }

    public List<TaskDTO> searchByTitle(String title) {

        return taskRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(MapperTasks::toTaskDTO)
                .toList();
    }


    public List<TaskDTO> searchByStatus(boolean status) {

        return taskRepository
                .findByCompleted(status)
                .stream()
                .map(MapperTasks::toTaskDTO)
                .toList();
    }

}