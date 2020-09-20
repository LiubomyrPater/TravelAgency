<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>Manage hotels</title>

    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/other_script.js"></script>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>
<div class="hotels-container" id="hotels-container">
    <table class="table table-hover table-bordered table-condensed table-striped">

        <tr>
            <th><spring:message code="hotel.name"/></th>
            <th><spring:message code="hotel.city"/></th>

        </tr>

        <tbody>
        <c:forEach items="${hotels}" var="hotel">
            <tr>
                <td>${hotel.name}</td>
                <td>${hotel.city}</td>
                <td><a href="<c:url value='/management/hotels/rooms?hotelId=${hotel.id}'/>" class="btn btn-success custom width">
                    <spring:message code="hotel.rooms"/></a></td>
                <td><a href="<c:url value='/management/hotels/rooms?hotelId=${hotel.id}'/>" class="btn btn-success custom width">
                    <spring:message code="hotel.manage"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
