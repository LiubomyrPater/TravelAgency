<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rooms</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/other_script.js"></script>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>

<div class="rooms-container" id="rooms-container">
    <table class="table table-hover table-bordered table-condensed table-striped">

        <tr>
            <th><spring:message code="room.number"/></th>
            <th><spring:message code="room.type"/></th>
            <th><spring:message code="room.bookings"/></th>
        </tr>

        <tbody>
        <c:forEach items="${rooms}" var="room">
            <tr>
                <td>${room.number}</td>
                <td>${room.type}</td>
                <td><a href="<c:url value='/management/hotels/rooms/roomBookings?roomId=${room.id}'/>" class="btn btn-success custom width">
                    <spring:message code="room.bookings"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


</body>
</html>
