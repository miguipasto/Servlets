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
    <h2>Consulata 1: Fase 3 (Country = ${fase13Bean.country}, Álbum=${fase13Bean.aid})</h2>
    <h2>Este es el resultado de la consulta:</h2>
    <ol>
        <c:forEach var="Song" items="${fase13Bean.songs}">
            <li><p>---Título = '${Song.title}' ---Idioma = '${Song.lang}' ---Géneros = '${Song.genre}' ---Compositor = '${Song.composer}'</p></li>
        </c:forEach>
    </ol>
    <br>
    <button type="button" id="inicio" onclick="window.location='P3M?p=${fase13Bean.password}&pphase=01'">Inicio</button>
    <button type="button" id="atras" onclick="window.location='P3M?p=${fase13Bean.password}&pphase=12&pcountry=${fase13Bean.country}'">Atras</button>
    <hr>
    <h4>Autor: Miguel Pastoriza Santaclara</h4>
    </body>
</html>
