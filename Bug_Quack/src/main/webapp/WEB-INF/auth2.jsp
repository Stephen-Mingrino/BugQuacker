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
<title>Bug Quack | Sign Up</title>
<link rel="stylesheet" type="text/css" href="/css/global2.css">
<link rel="stylesheet" type="text/css" href="/css/auth2.css">
</head>
<body>
	<div class="container">
		<div class="bugcont">
			<img class="bug" src="/assets/bug.png" alt="">
			<h2>Bug Quack</h2>
		</div>
	</div>
	<div>
		<div class="form-container">
			<div class="form1cont">

				<!-- ##### REGISTER ##### -->
				<div class="form">
					<form:form action="/register" method="post" modelAttribute="user">
						<h1>Register</h1>
						<div class="input-container ic1">
							<label class="placeholder">First Name</label>
							<div>
								<form:errors class="validation" path="firstName"></form:errors>
							</div>
							<div class="cut"></div>
							<form:input class="input" type="text" path="firstName" />
						</div>
						<div class="input-container ic2">
							<label class="placeholder">Last Name</label>
							<div>
								<form:errors class="validation" path="lastName"></form:errors>
							</div>
							<div class="cut"></div>
							<form:input class="input" placeholder=" " type="text"
								path="lastName" />
						</div>
						<div class="input-container ic2">
							<label class="placeholder">Email</label>
							<div>
								<form:errors class="validation" path="email"></form:errors>
							</div>
							<div class="cut"></div>
							<form:input class="input" placeholder=" " type="email"
								path="email" />
						</div>
						<div class="input-container ic3">
							<label class="placeholder" htmlFor="power_level">Password</label>
							<div>
								<form:errors class="validation" path="password"></form:errors>
							</div>
							<div class="cut"></div>
							<form:input class="input" placeholder=" " type="password"
								path="password" />
						</div>
						<div class="input-container ic4">
							<label class="placeholder" htmlFor="ultimate_attack">Confirm
								Password</label>
							<div>
								<form:errors class="validation" path="confirm"></form:errors>
							</div>
							<div class="cut"></div>
							<form:input class="input" placeholder=" " type="password"
								path="confirm" />
						</div>
						<button class="submit button">Register</button>
					</form:form>
				</div>
			</div>
			<div class="form2cont">


				<!-- ##### LOGIN ##### -->
				<div class="form2">
					<form:form action="/login" method="post" modelAttribute="loginUser">
						<h1>Login</h1>
						<div class="input-container ic2">
							<label class="placeholder">Email</label>
							<div>
								<form:errors class="validation" path="email"></form:errors>
							</div>
							<div class="cut"></div>
							<form:input class="input" placeholder=" " type="email"
								path="email" />
						</div>
						<div class="input-container ic3">
							<label class="placeholder" htmlFor="power_level">Password</label>
							<div>
								<form:errors class="validation" path="password"></form:errors>
							</div>
							<div class="cut"></div>
							<form:input class="input" placeholder=" " type="password"
								path="password" />
						</div>
						<button class="submit button">Login</button>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>