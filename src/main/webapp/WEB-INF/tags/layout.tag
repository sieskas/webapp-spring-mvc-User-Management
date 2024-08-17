<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} - User Management System</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
<nav class="bg-blue-600 text-white p-4">
    <div class="container mx-auto flex justify-between items-center">
        <a href="<c:url value='/'/>" class="text-xl font-bold">UMS</a>
        <div>
            <sec:authorize access="isAuthenticated()">
                <a href="<c:url value='/users'/>" class="mx-2 hover:underline">Users</a>
                <a href="<c:url value='/roles'/>" class="mx-2 hover:underline">Roles</a>
                <a href="<c:url value='/tokens'/>" class="mx-2 hover:underline">API</a>
                <form action="<c:url value='/logout'/>" method="post" class="inline">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="mx-2 hover:underline">Logout</button>
                </form>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                <a href="<c:url value='/login'/>" class="mx-2 hover:underline">Login</a>
                <a href="<c:url value='/register'/>" class="mx-2 hover:underline">Register</a>
            </sec:authorize>
        </div>
    </div>
</nav>

<main class="container mx-auto mt-8 p-4">
    <jsp:doBody/>
</main>
</body>
</html>
