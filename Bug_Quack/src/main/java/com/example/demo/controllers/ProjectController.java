package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Project;
import com.example.demo.models.User;
import com.example.demo.services.ProjectService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProjectController {
	
	@Autowired
	UserService userServ;
	@Autowired
	ProjectService projectServ;

	// ###### CREATE PROJECT #####
	@PostMapping("/createProject")
	public String createProject(
			@RequestParam("name") String name,
			@RequestParam("ownerId") Long ownerId,
			@RequestParam("memberIds") List<Long> memberIds) {

	    // Remove duplicates from memberIds using a Set
	    Set<Long> uniqueMemberIds = new LinkedHashSet<>(memberIds);
	    List<Long> memberIdsList = new ArrayList<>(uniqueMemberIds);

	    //Create project
		Project project = projectServ.createProject(name, ownerId, memberIdsList);
		
		//Update create by user stamp
		User activeUser = userServ.findUser(ownerId);
		projectServ.updatedByUser(project, activeUser);
		
		return "redirect:/dashboard";
	}
	
	// ###### DELETE PROJECT #####
	@RequestMapping("/deleteProject/{projectId}")
	public String deleteProject(
			@PathVariable Long projectId,
			HttpSession session) {
		
		// Route protection
		Project project = projectServ.findProject(projectId);
		User activeUser = userServ.findUser((Long) session.getAttribute("activeUserId"));
		
		if(project.getOwner() != activeUser) {
			return "forbidden.jsp";
		}
		
		projectServ.deleteProject(projectId);
		
		return "redirect:/dashboard";
	}
	
	// ###### EDIT PROJECT #####
	@RequestMapping("/editProject/{projectId}")
	public String editProject(
			@PathVariable Long projectId,
			@RequestParam("projectName") String projectName,
			HttpSession session) {
		
		Project project = projectServ.findProject(projectId);
		project.setName(projectName);
		projectServ.updateProject(project);

		// Updated by user stamp
		User activeUser = userServ.findUser( (Long) session.getAttribute("activeUserId"));
		projectServ.updatedByUser(project, activeUser);
		
		return "redirect:/project/" + projectId;
	}
	
	// ###### REMOVE TEAM MEMBER #####
	@RequestMapping("/removeMember/{projectId}")
	public String removeMember(
			@PathVariable Long projectId,
			HttpSession session) {
		
		Project project = projectServ.findProject(projectId);
		User activeUser = userServ.findUser( (Long) session.getAttribute("activeUserId"));
		
		// Route protection: if owner tries removing their self from team
		// Or if activeUser is not in project team
		if(project.getOwner() == activeUser || !project.getTeam().contains(activeUser)) {
			return "forbidden.jsp";
		}
		
		List<User> newTeam = project.getTeam();
		newTeam.remove(activeUser);
		project.setTeam(newTeam);
		projectServ.updateProject(project);
		projectServ.updatedByUser(project, activeUser);
		
		return "redirect:/dashboard";
	}
	
	// ###### ADD TEAM MEMBER #####
	@PostMapping("/addMember/{projectId}")
	public String addMember(
			@PathVariable Long projectId,
			@RequestParam("memberEmail") String memberEmail,
			HttpSession session) {
		
		Project project = projectServ.findProject(projectId);
		User newMember = userServ.findUserByEmail(memberEmail);
		User activeUser = userServ.findUser( (Long) session.getAttribute("activeUserId"));

		// Route protection
		if(session.getAttribute("activeUserId") == null || !project.getTeam().contains(activeUser))  {
			return "forbidden.jsp";
		}
		
		// If member is already in team, do nothing and redirect
		for(User member : project.getTeam()) {
			if(member == newMember) {
				return "redirect:/project/" + projectId;
			}
		}
		
		// If member wasn't already in team, add member to team and update team
		List<User> newTeam = project.getTeam();
		newTeam.add(newMember);
		project.setTeam(newTeam);
		projectServ.updateProject(project);
		
		// Updated by user stamp
		projectServ.updatedByUser(project, activeUser);
		
		return "redirect:/project/" + projectId;
	}


}
