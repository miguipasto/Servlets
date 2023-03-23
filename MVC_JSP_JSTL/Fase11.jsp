<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
    <meta charset='utf-8'>
    <link rel='stylesheet' href='./p3/p3.css'>
    <title>Servicio de consulta musical</title>
    </head>

    <body>
    <h1>Servicio de consulta de información musical</h1>
    <h2>Consulata 1: Fase 1</h2>
    <h2>Selecciona una Country:</h2>
    <ol>    
        <c:forEach var="country" items="${fase11Bean.countries}">
            <li><p><a href="?p=${fase11Bean.password}&pphase=12&pcountry=${country}">${country}</a></p></li>
        </c:forEach>
    </ol>
    <br>
    <button type="button" id="inicio" onclick="window.location='P3M?p=${fase11Bean.password}&pphase=01'">Inicio</button>
    <hr>
    <h4>Autor: Miguel Pastoriza Santaclara</h4>
    </body>
</html>
