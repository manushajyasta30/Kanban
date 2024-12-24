package com.assignment.kanban.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.kanban.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
