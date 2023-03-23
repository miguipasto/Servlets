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
    <h2>Ficheros con errores: </h2>
    <ul>
        <c:forEach var="error" items="${fase02Bean.error}">
            <li>${error}</li>
        </c:forEach>
    </ul>
    <h2>Ficheros con errores fatales: </h2>
    <ul>
        <c:forEach var="fatalError" items="${fase02Bean.fatalError}">
            <li>${fatalError}</li>
        </c:forEach>
    </ul>
    <br>
    <button type="button" id="atras" onclick="window.location='P3M?p=${fase02Bean.password}&pphase=01'">Atras</button>
    <hr>
    <h4>Autor: Miguel Pastoriza Santaclara</h4>
    </body>
</html>
