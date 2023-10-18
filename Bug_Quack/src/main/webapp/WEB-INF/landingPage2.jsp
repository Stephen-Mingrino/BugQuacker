<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) --> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/css/global2.css">
<link rel="stylesheet" type="text/css" href="/css/landingPage2.css">
</head>
<body>
 <div class="landing-container">
        <div class="landing-header">
            <div class="landing-header-left">
                <!-- bug icon -->
                <img src="../assets/bug.png" alt="Bug Logo" class="bug-icon">
                <h1>Bug Quack</h1>
            </div>
            <div class="landing-header-right">
                    <a href="/auth">
                        <button style="cursor:pointer;" class="landing-reg-log-btn">
                        Register</button>
                    </a>
                    <a href="/auth">
                        <button style="cursor:pointer;" class="landing-reg-log-btn">Login</button>
                    </a>
            </div>
        </div>
        <div class="landing-body">
                <a href="/demo">
                    <button class="landing-demo-btn" style="cursor:pointer;">Demo Account</button>
                </a>
        </div>
    </div>
</body>
</html>