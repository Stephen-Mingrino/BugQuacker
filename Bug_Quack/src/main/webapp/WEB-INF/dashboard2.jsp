<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bug Quack | ${activeUser.firstName } dashboard</title>
<link rel="stylesheet" type="text/css" href="/css/global2.css">
<link rel="stylesheet" type="text/css" href="/css/dashboard2.css">
<script type="text/javascript" src="/js/dashboard2.js"></script>
</head>
<body onload="setColorTheme(`${activeUser.colorTheme}`)">
	<div class="dashboard-container">
		<!-- HEADER -->
		<div class="dashboard-header">
			<div class="dashboard-header-left">
				<!-- bug-icon -->
				<img src="../assets/bug.png" alt="Bug Logo" class="bug-icon">
				<h1>Bug Quack</h1>
			</div>
			<div class="colorPalette">
					<div class="orange palatte " data-color="ff8600" onClick="changeColorTheme(this, 'ff8600')"></div>
					<div class="yellow palatte " data-color="ffc41b" onClick="changeColorTheme(this, 'ffc41b')"></div>
					<div class="blue palatte " data-color="00a4ed" onClick="changeColorTheme(this, '00a4ed')"></div>
					<div class="purple palatte " data-color="c300ff" onClick="changeColorTheme(this, 'c300ff')"></div>
			</div>
			<div class="dashboard-header-right">
				<!-- initials and logout icon -->
				<a href="/dashboard">
					<div class="initials-icon">${activeUser.initials }</div>
				</a>
				<button class="dashboard-logout-btn">
					<a href="/logout"> <img src="../assets/logout.png"
						alt="Logout Button" class="logout-icon">
					</a>
				</button>
			</div>
		</div>


		<!-- NEW PROJECT BUTTON-->
		<div class="dashboard-new-project">
			<div class="dashboard-new-project-btn-container">
				<button onclick="toggleForm()" class="dashboard-new-project-btn">
					<!-- add-icon -->
					<img src="../assets/add.png" alt="Add Icon"
						class="add-project-icon"> New Project
				</button>
			</div>



			<!-- NEW PROJECT FORM -->
			<div class="dashboard-form-container" id="newProjectForm">
				<form action="/createProject" method="post"
					class="dashboard-form card ">
					<!-- New members id's will get appended to the name field -->
					<input class="currentMemberIdsHiddenInput" type="hidden"
						name="memberIds" value="${activeUser.id }" /> <input
						type="hidden" name="ownerId" value="${activeUser.id }" />
					<div class="dashboard-form-group">
						<label for="members">Add members (optional). You can add
							members again later.</label>
						<!-- ##### ADD MEMEBERS ##### -->
						<p class="memberFeedback" style="opacity: 0%">Member not
							found.</p>
						<div class="members dashboard-add-members-bar">
							<div class="member owner highlighted-initials-icon">${activeUser.initials }</div>
							<div class="addedMembers members" style="display: flex;">
								<!--This is wear added members will get written to-->
							</div>
						</div>
						<div style='display: flex; align-items: center; margin-top: 1vw;'>
							<input type="text" class="emailInput dashboard-form-input"
								placeholder="Enter member's email"
								pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$"
								oninput="this.setCustomValidity('')">
							<button type="button" class="dashboard-form-add-member-btn"
								onClick="findUser(${activeUserId})">
								<img src="../assets/add.png" alt="Add Icon"
									class="add-member-icon">
							</button>
						</div>
					</div>
					<div class="dashboard-form-group">
						<label for="projectName">Project Name: </label>
						<p class="projectNameValidation"
							style="opacity: 0%; font-size: 10px;">Project name must be 1
							to 20 characters</p>

						<input type="text" name="name" class="dashboard-form-input"
							placeholder="Bug Tracker"
							oninvalid="this.setCustomValidity('Please enter a project name between 2 and 20 characters')"
							oninput="validateProjectName(this.value)">
					</div>
					<div class="dashboard-create-btn-container">
						<button class="createButton dashboard-form-create-btn"
							style="opacity: 50%;"true">Create</button>
					</div>
				</form>
			</div>
		</div>




		<!-- PROJECT LIST -->
		<div class="dashboard-project-list" id="allProjects">

			<!-- PROJECT -->
			<c:forEach var="project" items="${projects }">
				<div class="project">
					<div class="dashboard-project-list-btn-container">
						<button onclick="toggleProject(${project.id})"
							class="dashboard-project-list-btn">
							<a href="/project/${project.id }" style="text-decoration:none; color:white;">
								<div class="dashboard-project-list-btn-element">
									<!-- arrow-icon -->
									<img src="../assets/arrow.png" alt="Arrow Icon"
										class="view-project-icon"> ${project.name }
								</div>
							</a>
							<div class="dashboard-project-list-btn-element"
								style='position: absolute; margin-left: 22vw;'>
								<!-- initials icon and plus icon -->
								<c:forEach var="member" items="${project.team }">
									<c:if test="${member.id == project.owner.id }">
										<div class="highlighted-initials-icon">${member.initials }</div>
									</c:if>
									<c:if test="${member.id != project.owner.id }">
										<div class="initials-icon">${member.initials }</div>
									</c:if>
								</c:forEach>
							</div>
							<div class="dashboard-project-list-btn-element">
								<div class="dashboard-form-add-member-btn">
									<img src="../assets/add.png" alt="Add Icon"
										class="add-member-icon">
								</div>
							</div>
						</button>
					</div>

					<!-- TASKS -->
					<div class="dashboard-project-display" id="oneProjectDisplay" data-task-view="${project.id }">
						<p>Your Unresolved Tasks</p>
						<c:forEach var="task" items="${project.tasks }">
							<c:if test="${task.assignedUsers.contains(activeUser) && task.isResolved == false}">
								<div style="cursor:pointer;" class="dashboard-project-display-task" data-task-id="${task.id}" data-is-resolved="false" onclick="changeResolvedStatus(${task.id})">
									<input type="checkbox"> 
									<label class="instructions" for="taskId">${task.instructions }</label>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</c:forEach>

		</div>


	</div>
</body>
</html>