<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
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
            <th><spring:message code="users.email"/></th>
            <th><spring:message code="bookings.createMoment"/></th>
            <th><spring:message code="bookings.price"/></th>
            <th><spring:message code="bookings.earlyArrival"/></th>
            <th><spring:message code="bookings.arrival"/></th>
            <th><spring:message code="bookings.departure"/></th>
            <th><spring:message code="bookings.lateDeparture"/></th>
            <th><spring:message code="bookings.isPaid"/></th>
            <th></th>
            <th></th>
        </tr>

        <tbody>
        <c:forEach items="${bookings}" var="booking">
            <tr>
                <td>${booking.user}</td>
                <td>${booking.createMoment}</td>
                <td>${booking.price}</td>

                <td style="text-align: center; vertical-align: middle;">
                    <c:if test="${booking.earlyArrival}">
                        <input type="checkbox" name="earlyArrival" id="earlyArrival-status" checked="checked" disabled>
                    </c:if>
                    <c:if test="${!booking.earlyArrival}">
                        <input type="checkbox" name="earlyArrival" id="earlyArrival-status" disabled>
                    </c:if>
                </td>

                <td>${booking.arrival}</td>
                <td>${booking.departure}</td>

                <td style="text-align: center; vertical-align: middle;">
                    <c:if test="${booking.lateDeparture}">
                        <input type="checkbox" name="lateDeparture" id="lateDeparture-status" checked="checked" disabled>
                    </c:if>
                    <c:if test="${!booking.lateDeparture}">
                        <input type="checkbox" name="lateDeparture" id="lateDeparture-status" disabled>
                    </c:if>
                </td>

                <td style="text-align: center; vertical-align: middle;">
                    <c:if test="${booking.paid}">
                        <input type="checkbox" name="paid" id="pay-status" checked="checked" disabled>
                    </c:if>
                    <c:if test="${!booking.paid}">
                        <input type="checkbox" name="paid" id="pay-status" disabled>
                    </c:if>
                </td>

                <td><a class="btn btn-success custom width" onclick="edit_booking(${booking.id})">
                    <spring:message code="hotel.edit"/></a></td>
                <script>
                    function edit_booking(booking_id) {

                    }
                </script>

                <td><a class="btn btn-success custom width" onclick="delete_booking(${booking.id})">
                    <spring:message code="bookings.cancel"/></a></td>
                <script>
                    function delete_booking(booking_id) {
                        $.ajax({
                            url: "/bookingDeleteByID?bookingID=" + booking_id,
                            success: function(result){
                                if (result){
                                    alert("is done")
                                } else {
                                    alert("some problems")
                                }
                            }
                        });
                    }
                </script>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


</body>
</html>
