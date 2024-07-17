<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Role List</title>
</head>
<body>
<h1>Role List</h1>
<a href="<c:url value='/roles/form'/>">Add New Role</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="role" items="${roles}">
        <tr>
            <td>${role.id}</td>
            <td>${role.name}</td>
            <td>
                <a href="<c:url value='/roles/${role.id}/edit'/>">Edit</a>
                <form action="<c:url value='/roles/${role.id}/delete'/>" method="post" style="display:inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="submit" value="Delete" onclick="return confirm('Are you sure?');" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>