<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<layout:layout title="User Form">
    <div class="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl p-6">
        <h1 class="text-2xl font-bold mb-4">User Form</h1>
        <form:form action="${pageContext.request.contextPath}/users" method="post" modelAttribute="user">
            <form:hidden path="id"/>
            <div class="mb-4">
                <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Email:</label>
                <form:input path="email" id="email" required="true" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
            </div>
            <div class="mb-4">
                <label for="password" class="block text-gray-700 text-sm font-bold mb-2">Password:</label>
                <form:password path="password" id="password" required="true" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
            </div>
            <div class="mb-4">
                <label for="roleIds" class="block text-gray-700 text-sm font-bold mb-2">Roles:</label>
                <select name="roleIds" id="roleIds" multiple class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                    <c:forEach items="${allRoles}" var="role">
                        <option value="${role.id}" ${user.roles.contains(role) ? 'selected' : ''}>${role.name}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Save</button>
        </form:form>
    </div>
</layout:layout>