<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>

<layout:layout title="Power BI">
    <div class="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl p-6">
        <h2 class="text-2xl font-bold mb-4">Power BI</h2>

        <c:if test="${not empty message}">
            <div class="mb-4 p-4 rounded">
                <c:choose>
                    <c:when test='${messageType == "success"}'>
                        <div class="bg-green-100 text-green-700">
                                ${message}
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="bg-red-100 text-red-700">
                                ${message}
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <form action="<c:url value='/powerbi/createDataset'/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Créer votre dataset</button>
        </form>
    </div>
</layout:layout>
