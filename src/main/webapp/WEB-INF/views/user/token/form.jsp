<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<layout:layout title="Request Token">
    <div class="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl p-6">
        <h1 class="text-2xl font-bold mb-4">Request Token</h1>
        <form:form action="${pageContext.request.contextPath}/tokens" method="post" modelAttribute="token">
            <div class="mb-4">
                <label for="expirationEnumId" class="block text-gray-700 text-sm font-bold mb-2">Expiration:</label>
                <form:select path="expirationEnumId" id="expirationEnumId" name="expirationEnumId" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                    <form:options items="${expirationOptions}" itemValue="id" itemLabel="label" />
                </form:select>
            </div>
            <div class="mb-4">
                <label for="externalApplication.id" class="block text-gray-700 text-sm font-bold mb-2">External Application:</label>
                <form:select path="externalApplication.id" id="externalApplication" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                    <c:forEach var="application" items="${externalApplications}">
                        <form:option value="${application.id}" label="${application.name}" />
                    </c:forEach>
                </form:select>
            </div>
            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Request Token</button>
        </form:form>
    </div>
</layout:layout>
