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


import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

//    public ResponseEntity<String> createTask(Task task) {
//        try {
//            Task savedTask = taskRepository.save(task);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Task Created: " + savedTask.getTitle());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating task: " + e.getMessage());
//        }
//    }

//Need to check this
    public ResponseEntity<String> createTask(Task task) {
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


    public List<Task> getTask() {
    return taskRepository.findAll();
}
//
//    public Task getTaskF(Long Id) {
//    taskRepository.findById(Id);
//    if (taskRepository.existsById(Id)) {
//    return taskRepository.findById(Id).get();
//    }
//    return null;
//}

    public Task getTaskF(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }


//    public ResponseEntity<String> updateTask(Long id, Task updatedTask) {
//        Task existingTask = taskRepository.findById(id)
//                .orElseThrow(() -> new TaskNotFoundException(id));
//
//        existingTask.setTitle(updatedTask.getTitle());
//        existingTask.setDescription(updatedTask.getDescription());
//        existingTask.setStatus(updatedTask.getStatus());
//
//        taskRepository.save(existingTask);
//        return ResponseEntity.ok("Task updated successfully!");
//    }

//Need to check this
    public ResponseEntity<String> updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        // Ensure assigned user exists
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



    public ResponseEntity<String> deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task);
        return ResponseEntity.ok("Task deleted successfully!");
    }

}
