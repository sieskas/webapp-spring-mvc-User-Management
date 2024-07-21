<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<layout:layout title="Login">
    <div class="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl p-6">
        <h2 class="text-2xl font-bold mb-4">Login</h2>
<%--        <c:if test="${param.error != null}">--%>
<%--            <p class="text-red-500 mb-4">Invalid username and password.</p>--%>
<%--        </c:if>--%>
        <c:if test="${error != null}">
            <p class="text-red-500 mb-4">${error}</p>
        </c:if>
        <c:if test="${param.logout != null}">
            <p class="text-green-500 mb-4">You have been logged out successfully.</p>
        </c:if>
        <c:if test="${param.registered != null}">
            <p class="text-green-500 mb-4">Registration successful. You can now log in.</p>
        </c:if>

        <form action="<c:url value='/login'/>" method="post">
            <div class="mb-4">
                <label for="username" class="block text-gray-700 text-sm font-bold mb-2">Username:</label>
                <input type="text" id="username" name="username" required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"/>
            </div>
            <div class="mb-6">
                <label for="password" class="block text-gray-700 text-sm font-bold mb-2">Password:</label>
                <input type="password" id="password" name="password" required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"/>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Log in</button>
        </form>
        <div class="mt-4">
            <a href="<c:url value='/oauth2/authorization/google'/>" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block">Connect with Google</a>
        </div>
        <p class="mt-4">Don't have an account? <a href="<c:url value='/register'/>" class="text-blue-500 hover:underline">Register here</a></p>
    </div>
</layout:layout>