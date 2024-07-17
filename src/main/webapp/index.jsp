<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome to User Management System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; text-align: center; }
        h1 { color: #333; }
        .button { display: inline-block; padding: 10px 20px; margin: 10px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .button:hover { background-color: #0056b3; }
    </style>
</head>
<body>
<h1>Welcome to User Management System</h1>

<sec:authorize access="isAuthenticated()">
    <p>You are logged in as: <sec:authentication property="name" /></p>
    <a href="<c:url value='/home'/>" class="button">Go to Home</a>
    <form action="<c:url value='/logout'/>" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout" class="button" style="background-color: #dc3545;">
    </form>
</sec:authorize>

<sec:authorize access="!isAuthenticated()">
    <p>Please log in to access the system or register if you don't have an account.</p>
    <a href="<c:url value='/login'/>" class="button">Login</a>
    <a href="<c:url value='/register'/>" class="button" style="background-color: #28a745;">Register</a>
</sec:authorize>
</body>
</html>