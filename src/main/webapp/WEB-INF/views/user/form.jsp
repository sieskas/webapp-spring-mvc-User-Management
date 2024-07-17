<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>User Form</title>
</head>
<body>
<h1>User Form</h1>
<form:form action="${pageContext.request.contextPath}/users" method="post" modelAttribute="user">
    <form:hidden path="id"/>
    <div>
        <label for="email">Email:</label>
        <form:input path="email" id="email" required="true"/>
    </div>
    <div>
        <label for="password">Password:</label>
        <form:password path="password" id="password" required="true"/>
    </div>
    <div>
        <label for="roleIds">Roles:</label>
        <select name="roleIds" id="roleIds" multiple>
            <c:forEach items="${allRoles}" var="role">
                <option value="${role.id}" ${user.roles.contains(role) ? 'selected' : ''}>${role.name}</option>
            </c:forEach>
        </select>
    </div>
    <input type="submit" value="Save"/>
</form:form>
</body>
</html>