<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Bookings</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/other_script.js"></script>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

</head>
<body>
        <div class="bookings-container" id="bookings-container">
            <table class="table table-hover table-bordered table-condensed table-striped">

                <tr>
                    <th><spring:message code="bookings.room.hotel"/></th>
                    <th><spring:message code="bookings.room.type"/></th>
                    <th><spring:message code="bookings.room.number"/></th>
                    <th><spring:message code="bookings.price"/></th>
                    <th><spring:message code="bookings.createMoment"/></th>
                    <th><spring:message code="bookings.arrival"/></th>
                    <th><spring:message code="bookings.departure"/></th>
                    <th><spring:message code="bookings.earlyArrival"/></th>
                    <th><spring:message code="bookings.lateDeparture"/></th>
                </tr>

                <tbody>
                <c:forEach items="${bookings}" var="bookings">
                    <tr>
                        <td>${bookings.room.hotel}</td>
                        <td>${bookings.room.type}</td>
                        <td>${bookings.room.number}</td>
                        <td>${bookings.price}</td>
                        <td>${bookings.createMoment}</td>
                        <td>${bookings.arrival}</td>
                        <td>${bookings.departure}</td>
                        <td>${bookings.earlyArrival}</td>
                        <td>${bookings.lateDeparture}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
</div>


</body>
</html>






