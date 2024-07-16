<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Welcome to User Management System</h1>
<c:choose>
    <c:when test="${pageContext.request.userPrincipal.name != null}">
        <p>Welcome ${pageContext.request.userPrincipal.name}</p>
        <a href="<c:url value='/users'/>">Manage Users</a>
        <a href="<c:url value='/roles'/>">Manage Roles</a>
        <form action="<c:url value='/logout'/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit">Logout</button>
        </form>
    </c:when>
    <c:otherwise>
        <p>Please <a href="<c:url value='/login'/>">login</a> to access the system.</p>
    </c:otherwise>
</c:choose>
</body>
</html>