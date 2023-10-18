package com.example.demo.models;


import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="tasks")
public class Task {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
	    private String instructions;

		private boolean isResolved = false;
		
		private String markedResolvedBy;
		
	    @Column(updatable=false)
	    private Date createdAt;
	    private Date updatedAt;
	    
		@PrePersist
		protected void onCreate(){
			this.createdAt = new Date();
		}
		@PreUpdate
		protected void onUpdate(){
			this.updatedAt = new Date();
		}
		
		// Many to one projects
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="project_id")
		private Project project;

		// Many to one users
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="creator_id")
		private User creator;
		
		//Many to many users
		@ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "users_tasks", 
	        joinColumns = @JoinColumn(name = "task_id"), 
	        inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	    private List<User> assignedUsers;
	    
	    public Task() {
	        
	    }
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		public Date getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}
		public Project getProject() {
			return project;
		}
		public void setProject(Project project) {
			this.project = project;
		}
		public String getInstructions() {
			return instructions;
		}
		public void setInstructions(String instructions) {
			this.instructions = instructions;
		}
		
		public boolean getIsResolved() {
			return isResolved;
		}
		public void setIsResolved(boolean isResolved) {
			this.isResolved = isResolved;
		}
		public User getCreator() {
			return creator;
		}
		public void setCreator(User creator) {
			this.creator = creator;
		}
		public List<User> getAssignedUsers() {
			return assignedUsers;
		}
		public void setAssignedUsers(List<User> assignedUsers) {
			this.assignedUsers = assignedUsers;
		}
		public String getMarkedResolvedBy() {
			return markedResolvedBy;
		}
		public void setMarkedResolvedBy(String markedResolvedBy) {
			this.markedResolvedBy = markedResolvedBy;
		}

		
		
		
		
	
	    
	    
}
