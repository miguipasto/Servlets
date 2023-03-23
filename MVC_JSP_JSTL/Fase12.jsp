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
    <h2>Consulata 1: Fase 2 (Country = ${fase12Bean.country})</h2>
    <h2>Selecciona un Álbum:</h2>
    <ol>
        <c:forEach var="Album" items="${fase12Bean.albums}">
            <li><p><a href="?p=${fase12Bean.password}&pphase=13&pcountry=${fase12Bean.country}&paid=${Album.aid}">Álbum = '${Album.name}'</a> ---Año= '${Album.year}' ---Intérprete = '${Album.interprete}' ---Review = '${Album.review}'</p></li>
        </c:forEach>
    </ol>
    <br>
    <button type="button" id="inicio" onclick="window.location='P3M?p=${fase12Bean.password}&pphase=01'">Inicio</button>
    <button type="button" id="atras" onclick="window.location='P3M?p=${fase12Bean.password}&pphase=11'">Atras</button>
    <hr>
    <h4>Autor: Miguel Pastoriza Santaclara</h4>
    </body>
</html>


