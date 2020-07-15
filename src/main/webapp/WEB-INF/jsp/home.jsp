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

    <a class="btn btn-lg" href="/home/bookings"><spring:message code="users.bookings"/></a>
    <a class="btn btn-lg" onclick="document.forms['logoutForm'].submit()"><spring:message code="home.logout"/></a>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout"></form>
    </c:if>
    <sec:authorize access="hasRole('ROLE_MANAGER')">
        <h4><a class="nav-link" href="/management">Management</a></h4>
    </sec:authorize>

</div>
<%--<div class="box">
    <div class="heading">Пошук автомобіля</div>
    <div class="content">
        <form id="car-form" method="get" action="/search/car/">
            <div class="field">
                <label>Тип</label>
                <select id="id_qs-type" name="qs-type">
                    <option value="" selected="selected">---------</option>
                    <option value="car">Легковий</option>
                    <option value="truck">Вантажний</option>
                </select>
            </div>
            <div class="field">
                <label>Виробник</label>
                <select id="id_qs-manufacturer" name="qs-manufacturer"><option value="">---------</option><option value="1213">ACURA</option><option value="502">ALFA ROMEO</option><option value="504">AUDI</option><option value="511">BMW</option><option value="852">CADILLAC</option><option value="10389">CHERY</option><option value="602">CHEVROLET</option><option value="513">CHRYSLER</option><option value="514">CITROEN</option><option value="603">DACIA</option><option value="649">DAEWOO</option><option value="517">DAIHATSU</option><option value="521">DODGE</option><option value="745">FERRARI</option><option value="524">FIAT</option><option value="525">FORD</option><option value="10091">GEELY</option><option value="836">HOLDEN</option><option value="533">HONDA</option><option value="1214">HUMMER</option><option value="647">HYUNDAI</option><option value="1234">INFINITI</option><option value="538">ISUZU</option><option value="540">JAGUAR</option><option value="910">JEEP</option><option value="487">KAMAZ</option><option value="648">KIA</option><option value="22258">KING PISTON</option><option value="22545">KOYORAD</option><option value="545">LADA</option><option value="746">LAMBORGHINI</option><option value="546">LANCIA</option><option value="1292">LAND ROVER</option><option value="874">LEXUS</option><option value="1152">LINCOLN</option><option value="551">MAN</option><option value="222">MAYBACH</option><option value="552">MAZDA</option><option value="553">MERCEDES-BENZ</option><option value="625">MERCURY</option><option value="554">MG</option><option value="1231">MINI</option><option value="555">MITSUBISHI</option><option value="558">NISSAN</option><option value="561">OPEL</option><option value="563">PEUGEOT</option><option value="812">PONTIAC</option><option value="565">PORSCHE</option><option value="566">RENAULT</option><option value="748">ROLLS-ROYCE</option><option value="568">ROVER</option><option value="569">SAAB</option><option value="573">SEAT</option><option value="575">SKODA</option><option value="1149">SMART</option><option value="639">SSANGYONG</option><option value="576">SUBARU</option><option value="577">SUZUKI</option><option value="579">TOYOTA</option><option value="586">VOLVO</option><option value="587">VW</option></select>
            </div>
            <div class="field">
                <label>Модель</label>
                <select id="id_qs-model" name="qs-model"><option value="">---------</option></select>
            </div>

            <div class="submit">
                <button class="button" type="submit">Пошук</button>
            </div>
        </form>








        <script type="text/javascript">
            function qs_update_models(){
                var type = $('#id_qs-type').val();
                var brand =  $('#id_qs-manufacturer').val();
                var model = $('#id_qs-model').val();
                $('#id_qs-model').html('<option value="">---------</option>');
                param = {'brand_id': brand, 'type': type};
                $.get('/brands/models/', param, function(data) {
                    if(data) {
                        var html = '';
                        var group = ''
                        for (var i in data) {
                            var grp = data[i][1].split(' ')[0];
                            if (grp != group){
                                if (group != '') html += '</optgroup>';
                                html += '<optgroup label="'+grp+'">';
                            }
                            if (data[i][0] == model) { selected = 'selected' } else {selected = ''};
                            html += '<option value="'+data[i][0]+'"'+ selected +'>'+data[i][1]+'</option>';
                            group = grp;
                        }
                        html += '</optgroup>';
                        $('#id_qs-model').append(html);
                        $('#id_qs-model').trigger('refresh');
                    }
                });
            }
            function qs_update_brands(){
                var type = $('#id_qs-type').val()
                var brand = $('#id_qs-brand').val()
                $('#id_qs-manufacturer').html('<option value="">---------</option>');
                param = {'type': type}
                $.get('/brands/list/', param, function(data) {
                    if(data) {
                        var html = '';
                        for (var i in data){
                            if (data[i][0] == brand) { selected = 'selected' } else {selected = ''};
                            html += '<option value="'+data[i][0]+'"'+ selected +'>'+data[i][1]+'</option>';
                        }
                        $('#id_qs-manufacturer').append(html);
                        $('#id_qs-manufacturer').trigger('refresh');
                    }
                });
            }
            $(document).ready(function() {
                $('#car-form-advanced').click(function() {
                    $('#car-form-advanced-box').show('fast');
                    $(this).remove();
                    return false;
                });
                $('#id_qs-manufacturer').change(function() {
                    qs_update_models()
                });
                if ($('#id_qs-manufacturer').val()) qs_update_models();

                $('#id_qs-type').change(function() {
                    qs_update_brands();
                    qs_update_models();
                });
            });
        </script>
    </div>
</div>--%>

<div class="container">

    <form:form method="POST" modelAttribute="bookingForm" class="form-signin">

        <spring:bind path="city">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:select type="text" path="city" class="form-control">
                    <form:option value="--- Select ---" label="--- Select ---"/>
                    <form:options items="${cities}"/>
                </form:select>
                <form:errors path="city"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="hotel">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="hotel" class="form-control">
                    <form:option value="--- Select ---" label="--- Select ---"/>
                    <form:options items="${hotels}"/>
                </form:select>
                <form:errors path="hotel"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="room">
            <div class="form-group ${status.error ? 'has-error' : ''}" >
                <form:select type="text" path="room" class="form-control">
                    <form:option value="--- Select ---" label="--- Select ---"/>
                    <form:options items="${rooms}"/>
                </form:select>
                <form:errors path="room"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="price">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="price" class="form-control" placeholder="Price"></form:input>
            </div>
        </spring:bind>

        <spring:bind path="arrival">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="arrival" class="form-control"
                            placeholder="Arrival"></form:input>
                <form:errors path="arrival"></form:errors>
            </div>
        </spring:bind>
        <spring:bind path="earlyArrival">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="checkbox" path="earlyArrival" class="form-control"
                            placeholder="Early arrival"></form:input>
                <form:errors path="earlyArrival"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="departure">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="departure" class="form-control"
                            placeholder="departure"></form:input>
                <form:errors path="departure"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="lateDeparture">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="checkbox" path="lateDeparture" class="form-control"
                            placeholder="Late departure"></form:input>
                <form:errors path="lateDeparture"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>


</body>
</html>






