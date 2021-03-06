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
    <sec:authorize access="hasAnyRole( 'ROLE_ADMIN','ROLE_MANAGER')">
        <h4><a class="nav-link" href="/management">Management</a></h4>
    </sec:authorize>

</div>

<div class="container">

    <form:form method="POST" modelAttribute="bookingForm" class="form-signin" id="777">

        <sec:authorize access="hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')">
            <spring:bind path="user">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:select type="text" path="user" class="form-control">
                        <form:options items="${users}"/>
                    </form:select>
                </div>
            </spring:bind>
        </sec:authorize>

        <sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
            <spring:bind path="user">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input readonly="true" type="text" path="user" class="form-control"></form:input>
                </div>
            </spring:bind>
        </sec:authorize>

        <spring:bind path="city">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:select type="text" path="city" id="selectCity" class="form-control" onchange="city_select()">
                    <form:option value="" label="--- Select city ---"  id="default_city_field_value"/>
                    <form:options items="${cities}"/>
                </form:select>
            </div>
        </spring:bind>
        <script>
            function city_select() {
                $('#dateArrival').removeAttr('disabled');

                document.getElementById('selectHotel').setAttribute("disabled", "");
                document.getElementById('selectType').setAttribute("disabled", "");
                document.getElementById('selectRoom').setAttribute("disabled", "");
                document.getElementById('earlyArrival').setAttribute("disabled", "");
                document.getElementById('lateDeparture').setAttribute("disabled", "");
                document.getElementById('reserveButton').setAttribute("disabled", "");
                document.getElementById('default_city_field_value').setAttribute("disabled", "");

                $.ajax({
                    url: "home/citySelect?city=" + $("#selectCity").val(),
                    success: function(result){
                        var min_max = JSON.parse(result);
                        document.getElementById("priceMin").value = min_max.minPrice;
                        document.getElementById("priceMax").value = min_max.maxPrice;
                    }
                });
            }
        </script>

        <div class="row" id="priceRange">
            <div class="col-xs-12 col-sm-6">
                <input type="number" placeholder="price" value="${superMinPrice}" class="form-control" id="priceMin" onchange="change_price_min()">
                <label for="priceMin">Price min</label>
            </div>
            <script>
                function change_price_min() {

                }
            </script>
            <div class="col-xs-12 col-sm-6">
                <input type="number" placeholder="price" value="${superMaxPrice}" class="form-control" id="priceMax" onchange="change_price_max()">
                <label for="priceMax">Price max</label>
            </div>
            <script>
                function change_price_max() {

                }
            </script>

        </div>

        <div class="row">
            <div class="col-xs-12 col-sm-6" id="7">
                <spring:bind path="arrival">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="date" path="arrival" class="form-control" id="dateArrival" disabled="true" onchange="dateArrivalSelect()"></form:input>
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
                        <form:input type="date" path="departure" disabled="true" class="form-control" id="dateDeparture" onchange="dateDepartureSelect()"></form:input>
                        <form:errors path="departure"></form:errors>
                    </div>
                </spring:bind>
            </div>
            <script>
                function dateDepartureSelect() {
                    $.ajax({
                        url: "home/dateDepartureSelect?arrival=" + $("#dateArrival").val()
                        + "&departure=" + $("#dateDeparture").val()
                        + "&city=" + $("#selectCity option:selected").val()
                        + "&priceMin=" + $("#priceMin").val()
                        + "&priceMax=" + $("#priceMax").val(),
                        success: function(result){
                            clearDropDownHotel();
                            $.each(JSON.parse(result), function(index, value) {
                                if(!value){
                                    alert("Wrong date. Before arrival or before today");
                                }else {
                                    $('#selectHotel').removeAttr('disabled');
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
                <form:select type="text" path="hotel" id="selectHotel"
                             class="form-control" disabled="true" onclick="hotel_select()">

                </form:select>

            </div>
        </spring:bind>
        <script>
            function hotel_select() {

                document.getElementById('selectRoom').setAttribute("disabled", "");
                document.getElementById('earlyArrival').setAttribute("disabled", "");
                document.getElementById('lateDeparture').setAttribute("disabled", "");
                document.getElementById('reserveButton').setAttribute("disabled", "");

                $.ajax({
                    url: "home/hotelSelectForm?hotel=" + $("#selectHotel option:selected").val()
                    + "&city=" + $("#selectCity option:selected").val()
                    + "&arrival=" + $("#dateArrival").val()
                    + "&departure=" + $("#dateDeparture").val()
                    + "&priceMin=" + $("#priceMin").val()
                    + "&priceMax=" + $("#priceMax").val(),
                    success: function(result){
                        $('#selectType').removeAttr('disabled');//
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
                </form:select>
            </div>
        </spring:bind>
        <script>
            function type_select() {

                document.getElementById('earlyArrival').setAttribute("disabled", "");
                document.getElementById('lateDeparture').setAttribute("disabled", "");
                document.getElementById('reserveButton').setAttribute("disabled", "");

                $.ajax({
                    url: "home/typeSelectForm?hotel=" + $("#selectHotel option:selected").val()
                    + "&city=" + $("#selectCity option:selected").val()
                    + "&arrival=" + $("#dateArrival").val()
                    + "&departure=" + $("#dateDeparture").val()
                    + "&type=" + $("#selectType option:selected").val()
                    + "&priceMin=" + $("#priceMin").val()
                    + "&priceMax=" + $("#priceMax").val(),
                    success: function(result){

                        var rooms = JSON.parse(result);

                        var price = rooms[0].price;
                        rooms.shift();
                        document.getElementById("priceType").value = price;
                        $('#selectRoom').removeAttr('disabled');//
                        clearDropDownRoom();
                        $.each(rooms, function(index, value) {
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
                <form:input type="number" placeholder="price" value="" path="price" class="form-control" id="priceType"></form:input>
            </div>
        </spring:bind>


        <spring:bind path="room">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="room" id="selectRoom" class="form-control" disabled="true" onclick="room_select()">
                </form:select>
                <form:errors path="room"></form:errors>
            </div>
        </spring:bind>
        <script>
            function room_select() {
                //
                $.ajax({
                    url: "home/roomSelectForm?hotel=" + $("#selectHotel option:selected").val()
                    + "&city=" + $("#selectCity option:selected").val()
                    + "&arrival=" + $("#dateArrival").val()
                    + "&departure=" + $("#dateDeparture").val()
                    + "&type=" + $("#selectType option:selected").val()
                    + "&room=" + $("#selectRoom option:selected").val(),
                    success: function(result){
                        $('#reserveButton').removeAttr('disabled');//
                        if (((JSON.parse(result))[0]).earlyArrival){
                            $('#earlyArrival').removeAttr('disabled');
                        }
                        if (((JSON.parse(result))[1]).lateDeparture){
                            $('#lateDeparture').removeAttr('disabled');
                        }
                    }
                });
            }
        </script>
        <div class="row">
            <div class="col-xs-12 col-sm-6">
                <spring:bind path="earlyArrival">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:checkbox path="earlyArrival" class="form-control" disabled="true" id="earlyArrival" label="Early arrival"></form:checkbox>
                    </div>
                </spring:bind>
            </div>
            <div class="col-xs-12 col-sm-6">
                <spring:bind path="lateDeparture">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:checkbox path="lateDeparture" class="form-control" disabled="true" id="lateDeparture" label="Late departure"></form:checkbox>
                    </div>
                </spring:bind>
            </div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" id="reserveButton" disabled>Reserve</button>
    </form:form>
</div>
</body>
</html>






