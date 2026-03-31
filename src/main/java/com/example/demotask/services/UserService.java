package com.example.demotask.services;

import com.example.demotask.dto.CreateTaskRequest;
import com.example.demotask.entities.Task;
import com.example.demotask.entities.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong userIdGen = new AtomicLong();
    private final AtomicLong taskIdGen = new AtomicLong();

    public UserService() {
        users.add(new User(userIdGen.incrementAndGet(), "Alice", "alice@test.com", new ArrayList<>()));
        users.add(new User(userIdGen.incrementAndGet(), "Bob", "bob@test.com", new ArrayList<>()));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User addUser(User user) {
        user.setId(userIdGen.incrementAndGet());
        user.setTasks(new ArrayList<>());
        users.add(user);
        return user;
    }


    public User getUserById(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}