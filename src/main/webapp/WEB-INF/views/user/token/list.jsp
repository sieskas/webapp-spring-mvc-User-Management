<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>

<layout:layout title="Token List">
    <div class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
        <h1 class="text-2xl font-bold mb-4">Token List</h1>
        <a href="<c:url value='/tokens/form'/>" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mb-4 inline-block">Add New Token</a>
        <table class="w-full">
            <thead>
            <tr class="bg-gray-200">
                <th class="px-4 py-2">ID</th>
                <th class="px-4 py-2">Token</th>
                <th class="px-4 py-2">Expiration</th>
                <th class="px-4 py-2">External Application</th>
                <th class="px-4 py-2">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="token" items="${tokens}">
                <tr>
                    <td class="border px-4 py-2">${token.id}</td>
                    <td class="border px-4 py-2">${token.token}</td>
                    <td class="border px-4 py-2">${token.expirationEnum.label}</td>
                    <td class="border px-4 py-2">${token.externalApplication.name}</td>
                    <td class="border px-4 py-2">
                        <form action="<c:url value='/tokens/${token.id}/delete'/>" method="post" class="inline">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="text-red-500 hover:underline" onclick="return confirm('Are you sure?');">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</layout:layout>
