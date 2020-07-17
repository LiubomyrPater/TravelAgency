<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <meta charset="utf-8">
    <title>Travel Agency ${pageContext.request.userPrincipal.name}</title>
    <%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>--%>

    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/other_script.js"></script>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

</head>
<body>
<div class="text-center" role="toolbar" aria-label="Toolbar with buttons">

    <a class="btn btn-lg" href="/home/bookings"><spring:message code="users.myBookings"/></a>
    <a class="btn btn-lg" onclick="document.forms['logoutForm'].submit()"><spring:message code="home.logout"/></a>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout"></form>
    </c:if>
    <sec:authorize access="hasRole('ROLE_MANAGER')">
        <h4><a class="nav-link" href="/management">Management</a></h4>
    </sec:authorize>

</div>

<div class="container">

    <form:form method="POST" modelAttribute="bookingForm" class="form-signin">

        <sec:authorize access="hasRole('ROLE_MANAGER')">
            <spring:bind path="user">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:select type="text" path="user" class="form-control">
                        <form:option value="--- Select ---" label="--- Select user ---"/>
                        <form:options items="${users}"/>
                    </form:select>
                    <form:errors path="user"></form:errors>
                </div>
            </spring:bind>
        </sec:authorize>

        <spring:bind path="city">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:select type="text" path="city" class="form-control"  onchange="city_select(this)">
                    <form:option value="--- Select ---" label="--- Select city ---"/>
                    <form:options items="${cities}"/>
                </form:select>
                <form:errors path="city"></form:errors>
            </div>
        </spring:bind>
        <script>
            function city_select(obj) {
                console.log(obj.options[obj.selectedIndex].value)
            }
        </script>

        <spring:bind path="hotel">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="hotel" class="form-control">
                    <form:option value="--- Select ---" label="--- Select hotel ---"/>
                    <form:options items="${hotels}"/>
                </form:select>
                <form:errors path="hotel"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="arrival">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="arrival" class="form-control"
                            placeholder="Arrival"></form:input>
                <form:errors path="arrival"></form:errors>
            </div>
        </spring:bind>
        <spring:bind path="earlyArrival">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:checkbox path="earlyArrival" class="form-control" label="Early arrival"></form:checkbox>
                <form:errors path="earlyArrival"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="departure">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="departure" class="form-control"
                            placeholder="departure"></form:input>
                <form:errors path="departure"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="lateDeparture">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:checkbox path="lateDeparture" class="form-control" label="Late departure"></form:checkbox>
                <form:errors path="lateDeparture"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="typeRoom">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="typeRoom" class="form-control">
                    <form:option value="--- Select ---" label="--- Select type ---"/>
                    <form:options items="${roomTypes}"/>
                </form:select>
                <form:errors path="typeRoom"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="price">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="number" path="price" class="form-control" readonly="true"></form:input>
            </div>
        </spring:bind>

        <spring:bind path="room">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="room" class="form-control">
                    <form:option value="--- Select ---" label="--- Select room ---"/>
                    <form:options items="${rooms}"/>
                </form:select>
                <form:errors path="room"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Book</button>
    </form:form>

</div>


</body>
</html>






