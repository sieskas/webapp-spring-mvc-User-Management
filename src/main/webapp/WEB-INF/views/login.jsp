<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<c:if test="${param.error != null}">
    <p style="color: red;">Invalid username and password.</p>
</c:if>
<c:if test="${param.logout != null}">
    <p style="color: green;">You have been logged out successfully.</p>
</c:if>

<c:if test="${param.registered != null}">
    <p style="color: green;">Registration successful. You can now log in.</p>
</c:if>

<form action="<c:url value='/login'/>" method="post">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required/>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required/>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Log in</button>
</form>
<p>Don't have an account? <a href="<c:url value='/register'/>">Register here</a></p>
</body>
</html>