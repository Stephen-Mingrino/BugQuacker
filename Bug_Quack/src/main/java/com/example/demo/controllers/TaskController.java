package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Project;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.services.ProjectService;
import com.example.demo.services.TaskService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TaskController {
	
	@Autowired
	UserService userServ;
	@Autowired
	ProjectService projectServ;
	@Autowired
	TaskService taskServ;

	// ##### CREATE TASK #####
	@PostMapping("/createTask/{projectId}")
	public String createTask(
			@PathVariable Long projectId,
			@RequestParam("instructions") String instructions,
			@RequestParam("assignedUserIds") List<Long> assignedUserIds,
			HttpSession session) {

		// If instructions field is empty, do nothing.
		if(instructions.isEmpty()) {
			return "redirect:/project/" + projectId;
		}
		
		User activeUser = userServ.findUser( (Long) session.getAttribute("activeUserId"));
		Project project = projectServ.findProject(projectId);
		Task newTask = new Task();
		List<User> assignedUsers = new ArrayList<>();
		
		newTask.setProject(project);
		newTask.setInstructions(instructions);
		newTask.setCreator(activeUser);

		// Add each assigned user to the assignedUsers list
		for(Long userId : assignedUserIds) {
			assignedUsers.add(userServ.findUser(userId));
		}
		
		newTask.setAssignedUsers(assignedUsers);
		taskServ.createTask(newTask);
		
		// Updated by user stamp
		projectServ.updatedByUser(project, activeUser);
		
		return "redirect:/project/" + projectId;
	}
	
	
	// ##### CHANGE TASK RESOLVED STATUS #####
	@PostMapping("/changeResolvedStatus/{taskId}/{newStatus}")
	public String changeResolvedStatus(
			@PathVariable Long taskId,
			@PathVariable Boolean newStatus,
			HttpSession session) {
		
		User activeUser = userServ.findUser( (Long) session.getAttribute("activeUserId"));
		Task task = taskServ.findById(taskId);
		
		// Set isResolved to new status
		task.setIsResolved(newStatus);
		task.setMarkedResolvedBy(activeUser.getFirstName() + " " + activeUser.getLastName());
		taskServ.updateTask(task);
		
		// Updated by user stamp
		Project project = task.getProject();
		projectServ.updatedByUser(project, activeUser);
		
		return null;
	}

}
