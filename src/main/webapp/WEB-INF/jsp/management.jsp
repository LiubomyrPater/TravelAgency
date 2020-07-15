<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Admin</title>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/other_script.js"></script>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

</head>
<body>
<div>
    <button type="button" onclick='window.location.href = "/home"'>Home</button>
    <button type="button" onclick='window.location.href = "/management/addHotel"'>Add hotels</button>
    <button type="button" onclick='window.location.href = "/management/addRoom"'>Add rooms to the hotel</button>
    <button type="button" onclick='window.location.href = "/management/users"'>View users</button>
</div>

<div>
    <a class="btn btn-lg" onclick="document.forms['logoutForm'].submit()"><spring:message code="home.logout"/></a>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout"></form>
    </c:if>
</div>

</body>
</html>

