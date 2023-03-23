<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<html>
    <head>
    <meta charset='utf-8'>
    <link rel='stylesheet' href='./p3/p3.css'>
    <title>Servicio de consulta musical</title>
    </head>

    <body>
    <h1>Servicio de consulta de información musical</h1>
    <h1>Bienvenido a este servicio</h1>
    <br>
    <h2>Selecciona una consulta:</h2>
    <ul>
        <li><a href="?p=${fase01Bean.password}&pphase=02">Ver los ficheros erróneos</a></li>
        <li><a href="?p=${fase01Bean.password}&pphase=11">Consulta 1: Canciones pop de un Album de un Country</a></li>
    </ul>
    <br>
    <hr>
    <h4>Autor: Miguel Pastoriza Santaclara</h4>
    </body>
</html>
