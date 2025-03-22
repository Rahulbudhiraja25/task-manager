package com.assignment.TaskManager.services;

import com.assignment.TaskManager.entity.Task;
import com.assignment.TaskManager.entity.User;
import com.assignment.TaskManager.exception.TaskNotFoundException;
import com.assignment.TaskManager.repositories.TaskRepository;
import com.assignment.TaskManager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Create a new task with proper validation
    public ResponseEntity<String> createTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task title is required");
        }
        if (task.getAssignedTo() == null || task.getAssignedTo().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assigned user is required");
        }

        Optional<User> assignedUser = userRepository.findById(task.getAssignedTo().getId());
        if (assignedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assigned user does not exist");
        }

        task.setAssignedTo(assignedUser.get());
        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task Created: " + task.getTitle());
    }

    // ✅ Get paginated & sorted tasks
    public Page<Task> getTasks(int page, int size, String sortBy, String direction) {
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id"; // Default sorting field
        }

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findAll(pageable);
    }

    // ✅ Get a single task by ID
    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    // ✅ Update task with validation
    public ResponseEntity<String> updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (updatedTask.getTitle() == null || updatedTask.getTitle().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task title is required");
        }
        if (updatedTask.getAssignedTo() == null || updatedTask.getAssignedTo().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assigned user is required");
        }

        Optional<User> assignedUser = userRepository.findById(updatedTask.getAssignedTo().getId());
        if (assignedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assigned user does not exist");
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setAssignedTo(assignedUser.get());

        taskRepository.save(existingTask);
        return ResponseEntity.ok("Task updated successfully!");
    }

    // ✅ Delete task with error handling
    public ResponseEntity<String> deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task);
        return ResponseEntity.ok("Task deleted successfully!");
    }
}
