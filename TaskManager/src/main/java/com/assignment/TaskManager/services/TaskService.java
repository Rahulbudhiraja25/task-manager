package com.assignment.TaskManager.services;

import com.assignment.TaskManager.entity.Task;
import com.assignment.TaskManager.repositories.TaskRepository;
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

    public String createTask( Task task) {
    return "Task created "+task.getTitle();
}

    public List<Task> getTask() {
    return taskRepository.findAll();
}

    public Task getTaskF(Long Id) {
    taskRepository.findById(Id);
    if (taskRepository.existsById(Id)) {
    return taskRepository.findById(Id).get();
    }
    return null;
}

    public ResponseEntity<String> updateTask(Long id, Task updatedTask) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);

        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());

            taskRepository.save(existingTask);
            return ResponseEntity.ok("Task updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
    }

//    Here to implement Delete Task

}
