package com.assignment.TaskManager.repositories;
import com.assignment.TaskManager.entity.Task;
import com.assignment.TaskManager.enums.Status;
import com.assignment.TaskManager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByStatus(Status status, Pageable pageable);


    Page<Task> findByAssignedTo(User assignedTo, Pageable pageable);


    Page<Task> findByStatusAndAssignedTo(Status status, User assignedTo, Pageable pageable);
}
