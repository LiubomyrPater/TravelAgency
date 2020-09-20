<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Users</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/other_script.js"></script>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

</head>
<body>
        <div class="users-container" id="users-container">
            <table class="table table-hover table-bordered table-condensed table-striped">

                <tr>
                    <th><spring:message code="users.email"/></th>
                    <th><spring:message code="users.name"/></th>
                    <th><spring:message code="users.surname"/></th>
                    <th><spring:message code="users.phone"/></th>
                    <th><spring:message code="users.enabled"/></th>
                    <th><spring:message code="users.role"/></th>
                    <th><spring:message code="users.bookings"/></th>
                </tr>

                <tbody>
                <c:forEach items="${users}" var="users">
                    <tr>
                        <td>${users.email}</td>
                        <td>${users.name}</td>
                        <td>${users.surname}</td>
                        <td>${users.phoneNumber}</td>
                        <td>${users.enabled}</td>
                        <td>${users.role}</td>
                        <td><a href="<c:url value='/management/bookings?userId=${users.id}'/>" class="btn btn-success custom width">
                            <spring:message code="users.bookings"/></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
</div>

</body>
</html>






