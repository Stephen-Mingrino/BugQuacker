package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Project;
import com.example.demo.models.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    
    List<Task> findAll();
    Optional<Task> findById(Long id);
    List<Task> findAllByProject(Project project);
}
