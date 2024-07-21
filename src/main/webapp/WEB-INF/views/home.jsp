<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<layout:layout title="Home">
    <div class="text-center">
        <h1 class="text-3xl font-bold mb-4">Welcome to User Management System</h1>

        <sec:authorize access="isAuthenticated()">
            <p class="mb-4">You are logged in as: <span class="font-semibold">${username}</span></p>
            <a href="<c:url value='/users'/>" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mr-2">Manage Users</a>
            <a href="<c:url value='/roles'/>" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">Manage Roles</a>
        </sec:authorize>

        <sec:authorize access="!isAuthenticated()">
            <p class="mb-4">Please log in to access the system or register if you don't have an account.</p>
            <a href="<c:url value='/login'/>" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mr-2">Login</a>
            <a href="<c:url value='/register'/>" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">Register</a>
        </sec:authorize>
    </div>
</layout:layout>