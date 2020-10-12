<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>Sessions</title>

    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/other_script.js"></script>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>
<div class="hotels-container" id="hotels-container">
    <table class="table table-hover table-bordered table-condensed table-striped">

        <tr>
            <th>ID</th>
            <th>creation time</th>
            <th>last accessed time</th>
            <th>max inactive interval</th>
            <th>servlet context</th>
            <th>session context</th>
        </tr>

        <tbody>
        <c:forEach items="${sessionList}" var="session">
            <tr>
                <td>${session.id}</td>
                <td>${session.creationTime}</td>
                <td>${session.lastAccessedTime}</td>
                <td>${session.maxInactiveInterval}</td>
                <td>${session.servletContext}</td>
                <td>${session.sessionContext}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
