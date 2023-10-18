package com.example.demo.services;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Project;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repositories.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepo;
    
    // ##### CREATE TASK #####
    public Task createTask(Task task) {
    	Date now = new Date();
    	task.setUpdatedAt(now);
    	return taskRepo.save(task);
    }
   
    // ##### UPDATE TASK #####
    public Task updateTask(Task task) {
    	return taskRepo.save(task);
    }

    // ##### FIND TASK BY ID #####
    public Task findById(Long id) {
    	Optional<Task> optionalTask = taskRepo.findById(id);
    	return optionalTask.isPresent() ? optionalTask.get() : null;
    }
   


    // ##### FIND ACTIVE USER UNRESOLVED TASKS BY PROJECT #####
	  public List<Task> findActiveUserUnresolvedTasks(Project project, User activeUser){
		List<Task> projectTasks = taskRepo.findAllByProject(project);
		
		List<Task> activeUserTasks = projectTasks.stream()
				.filter(task -> task.getAssignedUsers().contains(activeUser))
				.filter(task -> !task.getIsResolved())
				.sorted(Comparator.comparing(Task::getCreatedAt).reversed()) 
				.collect(Collectors.toList());
			
		return activeUserTasks;
	  }    

	  // ##### FIND ACTIVE USER RESOLVED TASKS BY PROJECT #####
	  public List<Task> findActiveUserResolvedTasks(Project project, User activeUser){
		  List<Task> projectTasks = taskRepo.findAllByProject(project);
		  
		  List<Task> activeUserTasks = projectTasks.stream()
				  .filter(task -> task.getAssignedUsers().contains(activeUser))
				  .filter(task -> task.getIsResolved())
				  .sorted(Comparator.comparing(Task::getUpdatedAt).reversed()) 
				  .collect(Collectors.toList());
		  
		  return activeUserTasks;
	  }    
	  
	// ##### FIND TEAM UNRESOLVED TASKS BY PROJECT #####
	  public List<Task> findTeamUnresolvedTasks(Project project, User activeUser) {
	      List<Task> teamTasks = taskRepo.findAllByProject(project);
	      
	      // Remove task if the only member is the active user
	      teamTasks.removeIf(task -> task.getAssignedUsers().contains(activeUser) && task.getAssignedUsers().size() == 1);
	      teamTasks.removeIf(task -> task.getIsResolved() == true);

	      // Sort teamTasks by createdAt in descending order
	      List<Task> sortedTeamTasks = teamTasks.stream()
	              .sorted(Comparator.comparing(Task::getCreatedAt).reversed())
	              .collect(Collectors.toList());
	      
	      return sortedTeamTasks;
	  }

	  // ##### FIND TEAM RESOLVED TASKS BY PROJECT #####
	  public List<Task> findTeamResolvedTasks(Project project, User activeUser) {
		  List<Task> teamTasks = taskRepo.findAllByProject(project);
		  
	      // Remove task if the only member is the active user
	      teamTasks.removeIf(task -> task.getAssignedUsers().contains(activeUser) && task.getAssignedUsers().size() == 1);
	      teamTasks.removeIf(task -> task.getIsResolved() == false);
	      
	      // Sort teamTasks by updatedAt in descending order
	      List<Task> sortedTeamTasks = teamTasks.stream()
	              .sorted(Comparator.comparing(Task::getUpdatedAt).reversed())
	              .collect(Collectors.toList());
		  
		  return sortedTeamTasks;
	  }

    
    
}