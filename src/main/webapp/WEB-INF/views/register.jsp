<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
<h2>Register</h2>
<form:form action="${pageContext.request.contextPath}/register" method="post" modelAttribute="user">
    <div>
        <label for="email">Email:</label>
        <form:input path="email" id="email" required="true" />
    </div>
    <div>
        <label for="password">Password:</label>
        <form:password path="password" id="password" required="true" />
    </div>
    <div>
        <input type="submit" value="Register" />
    </div>
</form:form>
<div>
    <a href="/login">Already have an account? Login here</a>
</div>
</body>
</html>