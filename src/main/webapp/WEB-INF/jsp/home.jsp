<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <meta charset="utf-8">
    <title>Travel Agency ${pageContext.request.userPrincipal.name}</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

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

    <form:form method="POST" modelAttribute="bookingForm" class="form-signin" id="777">


        <sec:authorize access="hasRole('ROLE_MANAGER')">
            <spring:bind path="user">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:select type="text" path="user" class="form-control">
                        <form:options items="${users}"/>
                    </form:select>
                   <%-- <form:errors path="user"></form:errors>--%>
                </div>
            </spring:bind>
        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_USER')">
            <spring:bind path="user">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input readonly="true" type="text" path="user" class="form-control"></form:input>
                </div>
            </spring:bind>
        </sec:authorize>

        <spring:bind path="city">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:select type="text" path="city" id="selectCity" class="form-control" onchange="city_select()">
                    <form:option value="--- Select ---" label="--- Select city ---"/>
                    <form:options items="${cities}"/>
                </form:select>
                <%--<form:errors path="city"></form:errors>--%>
            </div>
        </spring:bind>
        <script>
            function city_select() {
                /*Refresh whole form*/
            }
        </script>

        <div class="row">
            <div class="col-xs-12 col-sm-6" id="7">
                <spring:bind path="arrival">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="date" path="arrival" class="form-control" id="dateArrival" onchange="dateArrivalSelect()"
                                    placeholder="Arrival" ></form:input>
                        <form:errors path="arrival"></form:errors>
                    </div>
                </spring:bind>
            </div>
            <script>
                function dateArrivalSelect() {
                    $.ajax({
                        url: "home/dateArrivalSelect?arrival=" + $("#dateArrival").val(),
                        success: function(result){
                            $.each(JSON.parse(result), function(index, value) {
                                if(!value){
                                    alert("Wrong date. Before today");
                                }else {
                                    $('#dateDeparture').removeAttr('disabled')
                                }
                            });
                        }
                    });
                }
            </script>

            <div class="col-xs-12 col-sm-6">
                <spring:bind path="departure">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="date" path="departure" disabled="true" class="form-control" id="dateDeparture" onchange="dateDepartureSelect()"
                                    placeholder="departure"></form:input>
                        <form:errors path="departure"></form:errors>
                    </div>
                </spring:bind>
            </div>
            <script>
                function dateDepartureSelect() {
                    $('#selectHotel').removeAttr('disabled');
                    $.ajax({
                        url: "home/dateDepartureSelect?arrival=" + $("#dateArrival").val()
                        + "&departure=" + $("#dateDeparture").val()
                        + "&city=" + $("#selectCity option:selected").val(),
                        success: function(result){
                            clearDropDownHotel();
                            $.each(JSON.parse(result), function(index, value) {
                                if(!value){
                                    alert("Wrong date. Before arrival or before today");
                                }else {
                                    createDropDownHotel(value.name);
                                }
                            });
                        }
                    });
                }
                function clearDropDownHotel() {
                    const dropDown = document.getElementById("selectHotel");
                    dropDown.innerHTML = "";
                }
                function createDropDownHotel(name) {
                    const dropDown = document.getElementById("selectHotel");
                    const option = document.createElement("option");
                    option.textContent = name;
                    dropDown.appendChild(option);
                }
            </script>
        </div>

        <spring:bind path="hotel">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="hotel" id="selectHotel" class="form-control" disabled="true" onclick="hotel_select()">

                </form:select>
                <%--<form:errors path="hotel"></form:errors>--%>
            </div>
        </spring:bind>
        <script>
            function hotel_select() {
                $('#selectType').removeAttr('disabled');
                $.ajax({
                    url: "home/hotelSelectForm?hotel=" + $("#selectHotel option:selected").val()
                    + "&city=" + $("#selectCity option:selected").val()
                    + "&arrival=" + $("#dateArrival").val()
                    + "&departure=" + $("#dateDeparture").val(),
                    success: function(result){
                        clearDropDownType();
                        $.each(JSON.parse(result), function(index, value) {
                            createDropDownType(value.name)
                        });
                    }
                });
            }
            function clearDropDownType() {
                const dropDown = document.getElementById("selectType");
                dropDown.innerHTML = "";
            }
            function createDropDownType(name) {
                const dropDown = document.getElementById("selectType");
                const option = document.createElement("option");
                option.textContent = name;
                dropDown.appendChild(option);
            }

        </script>

        <spring:bind path="typeRoom">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="typeRoom" class="form-control" disabled="true" id="selectType" onclick="type_select()">
                    <%--<form:option value="--- Select ---" label="--- Select type room ---"/>
                    <form:options items="${roomTypes}"/>--%>
                </form:select>
               <%-- <form:errors path="typeRoom"></form:errors>--%>
            </div>
        </spring:bind>
        <script>
            function type_select() {
                console.log("jdlkfsjfsd");
                $('#selectRoom').removeAttr('disabled');
                $.ajax({
                    url: "home/typeSelectForm?hotel=" + $("#selectHotel option:selected").val()
                    + "&city=" + $("#selectCity option:selected").val()
                    + "&arrival=" + $("#dateArrival").val()
                    + "&departure=" + $("#dateDeparture").val()
                    + "&type=" + $("#selectType option:selected").val,
                    success: function(result){
                        clearDropDownRoom();
                        $.each(JSON.parse(result), function(index, value) {
                            createDropDownRoom(value.number)
                        });
                    }
                });
            }
            function clearDropDownRoom() {
                const dropDown = document.getElementById("selectRoom");
                dropDown.innerHTML = "";
            }
            function createDropDownRoom(name) {
                const dropDown = document.getElementById("selectRoom");
                const option = document.createElement("option");
                option.textContent = name;
                dropDown.appendChild(option);
            }


        </script>

        <spring:bind path="price">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="number" path="price" class="form-control" readonly="true"></form:input>
            </div>
        </spring:bind>

        <spring:bind path="room">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="room" id="selectRoom" class="form-control" disabled="true" onclick="room_select()">
                    <%--<form:option value="--- Select ---" label="--- Select room ---"/>
                    <form:options items="${rooms}"/>--%>
                </form:select>
                <form:errors path="room"></form:errors>
            </div>
        </spring:bind>
        <script>
            function room_select() {
                $('#reserveButton').removeAttr('disabled');
            }
        </script>


        <div class="row">
            <div class="col-xs-12 col-sm-6">
                <spring:bind path="earlyArrival">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:checkbox path="earlyArrival" class="form-control" label="Early arrival"></form:checkbox>
                        <form:errors path="earlyArrival"></form:errors>
                    </div>
                </spring:bind>
            </div>
            <div class="col-xs-12 col-sm-6">
                <spring:bind path="lateDeparture">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:checkbox path="lateDeparture" class="form-control" label="Late departure"></form:checkbox>
                        <form:errors path="lateDeparture"></form:errors>
                    </div>
                </spring:bind>
            </div>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit" id="reserveButton" disabled>Reserve</button>

    </form:form>

</div>



</body>
</html>






