<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
</head>
<body>
<h1>User List</h1>
<a href="<c:url value='/users/form'/>">Add New User</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Email</th>
        <th>Roles</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.email}</td>
            <td>
                <c:forEach var="role" items="${user.roles}" varStatus="status">
                    ${role.name}<c:if test="${!status.last}">, </c:if>
                </c:forEach>
            </td>
            <td>
                <a href="<c:url value='/users/${user.id}/edit'/>">Edit</a>
                <form action="<c:url value='/users/${user.id}/delete'/>" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="submit" value="Delete" onclick="return confirm('Are you sure?');" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>