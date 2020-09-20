<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Bookings</title>

    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/other_script.js"></script>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>

<div class="bookings-container" id="bookings-container">
    <table class="table table-hover table-bordered table-condensed table-striped">

        <tr>
            <th><spring:message code="users.email"/></th>
            <th><spring:message code="bookings.createMoment"/></th>
            <th><spring:message code="bookings.price"/></th>
            <th><spring:message code="bookings.earlyArrival"/></th>
            <th><spring:message code="bookings.arrival"/></th>
            <th><spring:message code="bookings.departure"/></th>
            <th><spring:message code="bookings.lateDeparture"/></th>
            <%--<th><spring:message code="room.bookings"/></th>--%>
        </tr>

        <tbody>
        <c:forEach items="${bookings}" var="booking">
            <tr>
                <td>${booking.user}</td>
                <td>${booking.createMoment}</td>
                <td>${booking.price}</td>
                <td>${booking.earlyArrival}</td>
                <td>${booking.arrival}</td>
                <td>${booking.departure}</td>
                <td>${booking.lateDeparture}</td>
                <td><a href="<c:url value='/management/hotels/rooms/roomBookings?roomId=${room.id}'/>" class="btn btn-success custom width">
                    <spring:message code="hotel.edit"/></a></td>
                <td><a href="<c:url value='/management/hotels/rooms/roomBookings?roomId=${room.id}'/>" class="btn btn-success custom width">
                    <spring:message code="hotel.delete"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


</body>
</html>
