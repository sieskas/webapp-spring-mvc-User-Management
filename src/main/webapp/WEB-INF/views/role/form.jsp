<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <title>Role Form</title>
</head>
<body>
<h1>Role Form</h1>
<form:form action="${pageContext.request.contextPath}/roles" method="post" modelAttribute="role">
    <form:hidden path="id"/>
    <div>
        <label for="name">Name:</label>
        <form:input path="name" id="name" required="true"/>
    </div>
    <input type="submit" value="Save"/>
</form:form>
</body>
</html>