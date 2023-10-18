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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
//    @NotEmpty(message="First name is required!")
    @Size(min=2, max=30, message="2 to 30 characters.")
    private String firstName;
    
//    @NotEmpty(message="Last name is required!")
    @Size(min=2, max=30, message="2 to 30 characters.")
    private String lastName;
    
    @NotEmpty(message="Enter an email.")
    @Email(message="Invalid email.")
    private String email;
    
//    @NotEmpty(message="Password is required!")
    @Size(min=8, max=128, message="8 to 128 characters.")
    private String password;
    
    private String initials;
    
    private String colorTheme = "ff8600";

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
    
    @Transient
    private String confirm;

    //one to many projects
    @OneToMany(mappedBy="owner", fetch = FetchType.LAZY)
    private List<Project> leadProjects;
    
    // One to many tasks
    @OneToMany(mappedBy="creator", fetch = FetchType.LAZY)
    private List<Task> createdTasks;

    //many to many projects
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "users_projects", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects;
 
    //many to many tasks
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    		name = "users_tasks", 
    		joinColumns = @JoinColumn(name = "user_id"), 
    		inverseJoinColumns = @JoinColumn(name = "task_id")
    		)
    private List<Task> assignedTasks;

    
    public User() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
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
    public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public List<Project> getLeadProjects() {
		return leadProjects;
	}
	public void setLeadProjects(List<Project> leadProjects) {
		this.leadProjects = leadProjects;
	}
	public List<Task> getCreatedTasks() {
		return createdTasks;
	}
	public void setCreatedTasks(List<Task> createdTasks) {
		this.createdTasks = createdTasks;
	}
	public List<Task> getAssignedTasks() {
		return assignedTasks;
	}
	public void setAssignedTasks(List<Task> assignedTasks) {
		this.assignedTasks = assignedTasks;
	}
	public String getColorTheme() {
		return colorTheme;
	}
	public void setColorTheme(String colorTheme) {
		this.colorTheme = colorTheme;
	}

	

	
	
    
    
    
    
}
