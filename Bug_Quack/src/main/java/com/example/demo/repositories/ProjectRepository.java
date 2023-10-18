package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Project;
import com.example.demo.models.User;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    
    List<Project> findAll();
    Optional<Project> findById(Long id);
    
    List<Project> findAllByTeamNotContaining(User user);
    List<Project> findAllByTeamIsContaining(User user);
}
