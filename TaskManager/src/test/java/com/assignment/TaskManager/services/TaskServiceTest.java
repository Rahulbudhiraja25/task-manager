package com.assignment.TaskManager.services;

import com.assignment.TaskManager.entity.Task;
import com.assignment.TaskManager.entity.User;
import com.assignment.TaskManager.enums.Status;
import com.assignment.TaskManager.exception.TaskNotFoundException;
import com.assignment.TaskManager.repositories.TaskRepository;
import com.assignment.TaskManager.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setFirstName("Rahul");
        user.setLastName("Budhiraja");

        task = new Task();
        task.setId(1L);
        task.setTitle("Complete Project Report");
        task.setDescription("Submit by next week");
        task.setStatus(Status.IN_PROGRESS);
        task.setAssignedTo(user);
    }


    @Test
    void testCreateTask_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        ResponseEntity<String> response = taskService.createTask(task);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("Task Created: Complete Project Report", response.getBody());
    }

    @Test
    void testCreateTask_Fails_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = taskService.createTask(task);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Assigned user does not exist", response.getBody());
    }


    @Test
    void testGetTaskById_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTask(1L);

        assertNotNull(foundTask);
        assertEquals("Complete Project Report", foundTask.getTitle());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(1L));
    }


    @Test
    void testUpdateTask_Success() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated Description");
        updatedTask.setStatus(Status.COMPLETED);
        updatedTask.setAssignedTo(user);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        ResponseEntity<String> response = taskService.updateTask(1L, updatedTask);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Task updated successfully!", response.getBody());
    }

    @Test
    void testUpdateTask_Fails_WhenTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, updatedTask));
    }


    @Test
    void testDeleteTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<String> response = taskService.deleteTask(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Task deleted successfully!", response.getBody());

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void testDeleteTask_Fails_WhenTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
    }
    @Test
    void testGetTasks_Pagination_Success() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
        Page<Task> page = new PageImpl<>(List.of(task));

        when(taskRepository.findAll(pageable)).thenReturn(page);

        Page<Task> result = taskService.getTasks(0, 5, "createdAt", "desc");

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
    }


}
