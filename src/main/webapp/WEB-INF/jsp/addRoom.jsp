<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>



<html>
<head>
    <title>Add room</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/ajax.js"></script>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form:form method="POST" modelAttribute="addRoomForm" class="form-signin">

        <spring:bind path="hotel">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="hotel" class="form-control">
                    <form:options items="${hotels}" />
                </form:select>

                <form:errors path="hotel"></form:errors>
            </div>
        </spring:bind>


        <spring:bind path="type">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="type" class="form-control">
                    <form:options items="${types}" />
                </form:select>

                <form:errors path="type"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="number">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="number" class="form-control"
                            placeholder="Name" autofocus="true"></form:input>
                <form:errors path="number"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>
</div>
</body>
</html>




