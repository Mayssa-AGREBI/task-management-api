package com.example.demotask.services;

import com.example.demotask.dto.CreateTaskRequest;
import com.example.demotask.entities.Task;
import com.example.demotask.entities.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong taskIdGen = new AtomicLong();

    public TaskService() {
        Task t1 = new Task();
        t1.setId(taskIdGen.incrementAndGet());
        t1.setTitle("Learn Spring Boot");
        t1.setCompleted(false);
        t1.setUser(null);
        tasks.add(t1);

        Task t2 = new Task();
        t2.setId(taskIdGen.incrementAndGet());
        t2.setTitle("Build API");
        t2.setCompleted(true);
        t2.setUser(null);
        tasks.add(t2);
    }

    public List<Task> getAllTasks(int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, tasks.size());

        if (start > tasks.size()) {
            return Collections.emptyList();
        }

        return tasks.subList(start, end);
    }


    public Task updateStatus(Long id, boolean completed) {
        Task task = tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setCompleted(completed);
        return task;
    }

    public Task addTask(Task task) {
        task.setId(taskIdGen.incrementAndGet());
        // Ensure task has a user set (optional)
        if (task.getUser() == null) {
            task.setUser(null);
        }
        tasks.add(task);
        return task;
    }

    public Task findTaskById(Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTask(Long id, CreateTaskRequest request) {

        Task task = tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(request.getTitle());

       // task.setCompleted(request.completed());

        return task;
    }

    public void deleteTask(Long id) {

        Task task = tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));

        tasks.remove(task);
    }

    public Task assignTask(Long taskId, User user) {
        Task task = tasks.stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setUser(user);
        return task;
    }

}