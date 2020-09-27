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
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.email}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.phoneNumber}</td>
                        <td style="text-align: center; vertical-align: middle;">
                            <c:if test="${user.enabled}">
                                <input type="checkbox" name="enabled" id="enable-status" checked="checked" onchange="change_status('${user.email}')">
                            </c:if>
                            <c:if test="${!user.enabled}">
                                <input type="checkbox" name="enabled" id="enable-status" onchange="change_status('${user.email}')">
                            </c:if>
                            <script>
                                function change_status(email) {
                                    $.ajax({
                                        url: "users/changeStatus?user=" + email,
                                        success: function(result){
                                            console.log(result)
                                        }
                                    });
                                }
                            </script>
                        </td>
                        <td>${user.role}</td>
                        <td><a href="<c:url value='/management/bookings?userId=${user.id}'/>" class="btn btn-success custom width">
                            <spring:message code="users.bookings"/></a></td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
        </div>
</div>


</body>
</html>






