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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="projects")
public class Project {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
//		@NotEmpty(message="name is required!")
	    private String name;

		private String updatedBy;
	    
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
		

		//One to many tasks
		@OneToMany(mappedBy="project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    private List<Task> tasks;
		
		// Many to one users
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="owner_id")
		private User owner; //'owner' must match mappedBy="owner" in User class
		
		//Many to many users
		@ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "users_projects", 
	        joinColumns = @JoinColumn(name = "project_id"), 
	        inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	    private List<User> team;
	    
	    public Project() {
	        
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
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<User> getTeam() {
			return team;
		}
		public void setTeam(List<User> team) {
			this.team = team;
		}
		public User getOwner() {
			return owner;
		}
		public void setOwner(User owner) {
			this.owner = owner;
		}
		public List<Task> getTasks() {
			return tasks;
		}
		public void setTasks(List<Task> tasks) {
			this.tasks = tasks;
		}
		public String getUpdatedBy() {
			return updatedBy;
		}
		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
		}
		
		
		
	
	    
	    
}
