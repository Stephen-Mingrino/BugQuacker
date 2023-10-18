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
<title>Bug Quack | ${project.name }</title>
<link rel="stylesheet" type="text/css" href="/css/global2.css">
<link rel="stylesheet" type="text/css" href="/css/project2.css">
<link rel="stylesheet" type="text/css" href="/css/auth2.css">
<script type="text/javascript" src="/js/project2.js"></script>
</head>
<body class="wrapper" onload="setColorTheme(`${activeUser.colorTheme}`)">
	<div class="projectContainer">
		<div>
			<div class="bugcont">
				<img class="bug" src="/assets/bug.png" alt="">
				<h2>Bug Quack</h2>
			</div>
			<div class="colorPalette">
					<div class="orange palatte " data-color="ff8600" onClick="changeColorTheme(this, 'ff8600')"></div>
					<div class="yellow palatte " data-color="ffc41b" onClick="changeColorTheme(this, 'ffc41b')"></div>
					<div class="blue palatte " data-color="00a4ed" onClick="changeColorTheme(this, '00a4ed')"></div>
					<div class="purple palatte " data-color="c300ff" onClick="changeColorTheme(this, 'c300ff')"></div>
			</div>
		</div>
		<div class="logoutCont" style="gap: 1vw">
			<a href="/dashboard"> <input class="ProjPlaceholder"
				placeholder="${activeUser.initials }">
			</a> <a href="/logout"> <img class="Logout" src="/assets/logout.png"
				alt="">
			</a>
		</div>
	</div>


	<!-- PROJECT TITLE -->
	<div class="photovote1">
		<h1>${project.name }</h1>
		<div style="display: flex;">
			<div onclick="showEditName()">
				<img class="imgNoBorder" src="/assets/edit.png" alt="">
			</div>
			<c:if test="${project.owner == activeUser }">
				<a href="/deleteProject/${project.id }"> <img class="imgNoBorder"
					src="/assets/delete.png" alt="" />
				</a>
			</c:if>
			<c:if test="${project.owner != activeUser }">
				<a href="/removeMember/${project.id }"> <img class="imgNoBorder"
					src="/assets/delete.png" alt="" />
				</a>
			</c:if>
		</div>
	</div>


	<!-- EDIT PROJECT -->
	<div class="editName photovote2" style="display: none">
		<form action="/editProject/${project.id }">
			<input style="color: white;" class="projinput1" type="text"
				name="projectName">
			<button type="submit" class="projbtn marg">Save</button>
			<button type="button" class="projbtn marg" onClick="hideEditName()">Cancel</button>
		</form>

	</div>


	<!-- TEAM -->
	<div class="card teamCard">
		<p class="pmarg">Last updated Oct. 16th random time</p>
		<div class="teamCardFlex">
			<h2>Team</h2>
			<c:forEach var="member" items="${project.team }">
				<input class="ProjPlaceholder2 marg "
					placeholder="${member.initials }">
			</c:forEach>
			<img class="add marg" src="/assets/add.png" alt=""
				onclick="showAddEmail()">
		</div>
	</div>
	<div class="addEmail" style="display: none;">
		<form action="/addMember/${project.id }" method="post">
			<input class="emailInput" name="memberEmail" type="email"
				placeholder="enter member's email" />
			<button type="submit" class="findUserButton">+</button>
		</form>
	</div>
	<!-- <div class="card teamCard2">
            <img class="ProjPlaceholder3 placemargin2 add" src="assets/add.png" alt="">
            <h2>New Task</h2>
        </div> 


	<!-- ADD TASK -->
	<div class="card teamCard3">
		<div class="teamCard3top">
			<button class="ProjPlaceholder4" onclick="toggleForm()">
				<img class="placemargin2 add" src="/assets/add.png" alt="">
			</button>
			<h2 class="h2marg">New Task</h2>
		</div>
		<div class="newTask" id="newTask">
			<p class="p1">Members highlighted will be assigned new task</p>

			 
			<!-- <div class="teamCardFlex teamBox" style="background-color: red;">
				<input class="ProjPlaceholderHighlight placemargin "
					placeholder="SM"><br> <input
					class="ProjPlaceholder2 marg2 " placeholder="GJ"> <input
					class="ProjPlaceholder2 marg2" placeholder="VN"><br>
			</div> -->
			 
			<div class="members teamCardFlex teamBox">
					<c:forEach var="member" items="${project.team }">
						<div class="member ProjPlaceholder2 marg2" data-is-selected="false" data-userId="${member.id }" onClick="selectUser(this)">${member.initials }</div>
					</c:forEach>
			</div>

			<p class="p1">Instructions:</p>
			<form action="/createTask/${project.id }" method="post">
					<!-- selected members id's will get written to hidden input value -->
				<input type="hidden" class="hiddenInput" name="assignedUserIds" value=""  />
				<textarea name="instructions" id="" cols="55" rows="13"></textarea>
				<button class="assignButton projbtn2" disabled="true" style="opacity:50%;">Assign</button>
			</form>
		</div>
	</div>



	<!-- YOUR TASKS -->
	<div class="teamCard4 card">
		<div class="h2task">
			<h2>Your Tasks</h2>
		</div>
		<!-- UNRESOLVED TASKS -->
		<div class="checkFlex">
				<c:forEach var="task" items="${activeUserUnresolvedTasks }">
					<div  class="task checkflex1" data-is-resolved="${task.isResolved }" data-task-id="${task.id }" onClick="changeResolvedStatus(${task.id})">
						<div class="checkbox Box">
							<img style="display:none" src="/assets/check.png" alt="" data-checkmark-id="${task.id }"  />
						</div>
						<div>
							<p class="instructions p2">${task.instructions }</p>
							<div class="stamp">
								<p class="p3" style="margin-left:1vw; font-size:0.8rem">Create by ${task.creator.firstName } ${task.creator.lastName} on 
									<fmt:formatDate value="${task.createdAt}" pattern="MMM dd"/>
									at
									<fmt:formatDate value="${task.createdAt}" pattern="h:mma"/>
								</p>									
							</div>
						</div>
					</div>
				</c:forEach>
			<!-- RESOLVED TASKS -->
				<c:forEach var="task" items="${activeUserResolvedTasks }">
					<div  class="task checkflex1" style="opacity:0.5;" data-is-resolved="${task.isResolved }" data-task-id="${task.id }" onClick="changeResolvedStatus(${task.id})">
						<div class="checkbox Box">
							<img src="/assets/check.png" alt="" data-checkmark-id="${task.id }"  />
						</div>
						<div>
							<p class="instructions p2 p4">${task.instructions }</p>
							<div class="stamp p3">
								<p style="margin-left:1vw; font-size:0.8rem">Marked resolved by ${task.markedResolvedBy } on 
									<fmt:formatDate value="${task.updatedAt}" pattern="MMM dd"/>
									at
									<fmt:formatDate value="${task.updatedAt}" pattern="h:mma"/>
								</p>									
							</div>
						</div>
					</div>
				</c:forEach>
		</div>
	</div>



	<!-- TEAM TASKS -->
	<div class="teamCard4 card">
		<div class="h2task">
			<h2>Team Tasks</h2>
		</div>
		<!-- UNRESOLVED TASKS -->
		<div class="checkFlex">
				<c:forEach var="task" items="${teamUnresolvedTasks }">
					<div  class="task checkflex1" data-is-resolved="${task.isResolved }" data-task-id="${task.id }" onClick="changeResolvedStatus(${task.id})">
						<div class="checkbox Box">
							<img style="display:none" src="/assets/check.png" alt="" data-checkmark-id="${task.id }"  />
						</div>
						<div>
							<p class="instructions p2">${task.instructions }</p>
							<div class="stamp pflex">
								<p class="p3" style="margin-left:1vw; font-size:0.8rem">Create by ${task.creator.firstName } ${task.creator.lastName} on 
									<fmt:formatDate value="${task.createdAt}" pattern="MMM dd"/>
									at
									<fmt:formatDate value="${task.createdAt}" pattern="h:mma"/>
								</p>
								<c:forEach var="member" items="${task.assignedUsers }">
									<c:if test="${member != activeUser }">
										<input class="ProjPlaceholder5 marg3 " placeholder="${member.initials }">
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</c:forEach>
				
				<!-- RESOLVED TASKS -->
				<c:forEach var="task" items="${teamResolvedTasks }">
					<div  class="task checkflex1" style="opacity:0.5;" data-is-resolved="${task.isResolved }" data-task-id="${task.id }" onClick="changeResolvedStatus(${task.id})">
						<div class="checkbox Box">
							<img src="/assets/check.png" alt="" data-checkmark-id="${task.id }"  />
						</div>
						<div>
							<p class="instructions p2 p4 ">${task.instructions }</p>
							<div class="stamp pflex p3">
								<p style="margin-left:1vw; font-size:0.8rem">Marked resolved by ${task.markedResolvedBy } on 
									<fmt:formatDate value="${task.updatedAt}" pattern="MMM dd"/>
									at
									<fmt:formatDate value="${task.updatedAt}" pattern="h:mma"/>
								</p>
								<c:forEach var="member" items="${task.assignedUsers }">
									<c:if test="${member != activeUser }">
										<input class="ProjPlaceholder5 marg3 " placeholder="${member.initials }">
									</c:if>
								</c:forEach>								
							</div>
						</div>
					</div>
				</c:forEach>
		</div>
	<script src="projects.js" type="text/javascript"></script>




</body>
</html>