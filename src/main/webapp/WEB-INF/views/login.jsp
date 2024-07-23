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
            <a href="<c:url value='/oauth2/authorization/google'/>" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block">
                <svg class="inline w-5 h-5 mr-2" viewBox="0 0 48 48">
                    <path fill="#EA4335" d="M24 9.5c3.4 0 6.2 1.2 8.1 3.2l6-6c-3.7-3.5-8.5-5.7-14.1-5.7-8.9 0-16.3 5.8-19 13.7l7.4 5.7c1.2-4 4.8-7.1 9.6-7.1z"></path>
                    <path fill="#34A853" d="M46.9 24.1c0-1.3-.1-2.6-.4-3.8H24v7.4h13.1c-.6 3.3-2.6 6.1-5.6 7.9l7.3 5.6c4.3-4 6.7-9.9 6.7-16.9z"></path>
                    <path fill="#4A90E2" d="M35.6 35.2c-1.5 1.2-3.4 2.1-5.6 2.1-4.1 0-7.6-2.6-8.9-6.2l-7.4 5.7c2.8 5.5 8.5 9.2 15.3 9.2 4.3 0 8.3-1.5 11.4-4.1l-7.2-5.7z"></path>
                    <path fill="#FBBC05" d="M10.9 24c0-1.2.1-2.3.3-3.4l-7.4-5.7c-1.4 2.8-2.1 5.9-2.1 9.1s.7 6.3 2.1 9.1l7.4-5.7c-.2-1.1-.3-2.2-.3-3.4z"></path>
                </svg>
                Connect with Google
            </a>
            <a href="<c:url value='/oauth2/authorization/microsoft'/>" class="bg-blue-700 hover:bg-blue-900 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline inline-block mt-2">
                <svg class="inline w-5 h-5 mr-2" viewBox="0 0 24 24">
                    <path fill="#FFFFFF" d="M0 0h24v24H0z" fill="none"/>
                    <path fill="#FFFFFF" d="M0 0h24v24H0z" fill="none"/>
                    <path fill="#F25022" d="M12 12H2.5V2.5H12V12z"></path>
                    <path fill="#00A4EF" d="M21.5 2.5v9.5H12V2.5h9.5z"></path>
                    <path fill="#7FBA00" d="M21.5 21.5H12v-9.5h9.5v9.5z"></path>
                    <path fill="#FFB900" d="M12 21.5H2.5V12H12v9.5z"></path>
                </svg>
                Connect with Microsoft
            </a>
        </div>
        <p class="mt-4">Don't have an account? <a href="<c:url value='/register'/>" class="text-blue-500 hover:underline">Register here</a></p>
    </div>
</layout:layout>