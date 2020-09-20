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
    <div class="panel" id="bottom-menu" >
        <button type="button" onclick=show_elements_menu()>Menu</button>
    </div>
    <script>
        function show_elements_menu() {
            document.getElementById('bottom-panel').style.display = "block"
            document.getElementById('add-hotel-form').style.display = "none"
        }
    </script>
    <div class="panel" id="bottom-panel" >
    <button type="button" onclick='window.location.href = "/home"'>Home</button>
    <button type="button" onclick=add_hotel()>Add hotels</button>
    <button type="button" onclick='window.location.href = "/management/addRoom"'>Add rooms to the hotel</button>
    <button type="button" onclick='window.location.href = "/management/users"'>View users</button>
    <button type="button" onclick='window.location.href = "/management/hotels"'>Manage hotels</button>
    </div>

    <script>
        function add_hotel() {
            document.getElementById('bottom-panel').style.display = "none"
            document.getElementById('add-hotel-form').style.display = "block"

            $.ajax({
                url: "/restManagement/getCities",
                success: function (result) {
                    $.each(result, function(index, city) {
                        createDropDownCity(city)
                    });

                }
            });
        }

        function createDropDownCity(name) {
            const dropDown = document.getElementById("cities");
            const option = document.createElement("option");
            option.textContent = name;
            dropDown.appendChild(option);
        }
    </script>

    <div class="container" id="add-hotel-form" style="display: none">
        <form method="POST"  class="form-signin" >
            <div class="form-group" >
                <select type="text" path="city" class="form-control" id="cities">
                </select>
            </div>
            <div class="form-group">
                <input type="text" path="name" class="form-control" placeholder="Name" autofocus="true">
                </input>
            </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form>
    </div>

</div>
</body>
</html>

