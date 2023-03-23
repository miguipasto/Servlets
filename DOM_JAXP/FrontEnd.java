package p2;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import javax.xml.xpath.*;
import java.util.*;

import p2.DataModel.*;
import p2.Sint92P2.*;

public class FrontEnd {

    DataModel modelo = new DataModel();

    /* Screen en modo auto cuando no se ha introducido contraseña */
    public void autoFaltaContraseña(PrintWriter out) { 
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongRequest>no passwd</wrongRequest>");
    }

    /* Screen en modo auto para cuando la contraseña introducida es errónea */
    public void autoContraseñaErronea(PrintWriter out){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongRequest>bad passwd</wrongRequest>");
    }

    /* Screen en modo auto para cuando el usario quiera acceder a algo sin escribir los parámetros necesarios */
    public void autoFaltaParametro(PrintWriter out, String parametro){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongRequest>no param:"+parametro+"</wrongRequest>");
    }

    /* Screen en modo auto de la Fase01 */
    public void autoFase01(PrintWriter out){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<service>");
        out.println("<status>OK</status>");
        out.println("</service>");
    }

    /* Screen en modo auto para la Fase 02: Mostramos los archivos muml erróneos */
    public void autoFase02(PrintWriter out, TreeMap<String,String> fatalError,TreeMap<String,String> error){

        Set<Map.Entry<String, String>> entrySetError = error.entrySet();
        List<Map.Entry<String, String>> entryListError = new ArrayList<>(entrySetError);

        Set<Map.Entry<String, String>> entrySetFatalError = fatalError.entrySet();
        List<Map.Entry<String, String>> entryListFatalError = new ArrayList<>(entrySetFatalError);

        String nombre_xml = new String();

        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongDocs>");

        out.println("<errors>");
        for (int index = 0; index<entryListError.size(); index++){
            nombre_xml = entryListError.get(index).getKey();
            out.println("<error><file>"+nombre_xml+"</file></error>");
        }
        out.println("</errors>");

        out.println("<fatalerrors>");
        for (int index = 0; index<entryListFatalError.size(); index++){
            nombre_xml = entryListFatalError.get(index).getKey();
            out.println("<fatalerror><file>"+nombre_xml+"</file></fatalerror>");
        }
        out.println("</fatalerrors>");
        out.println("</wrongDocs>");
    }

    /* Screen en modo auto para la Fase 11 : Mostramos todos los países encontrados */
    public void autoFase11(PrintWriter out, ArrayList<String> countries){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<countries>");
        for (int cn=0; cn < countries.size(); cn++){
            out.println("<country>"+countries.get(cn)+"</country>");
        }
        out.println("</countries>");

    }

    /* Screen en modo auto par la Fase 12 : Mostramos los albumes según el pais seleccionado */
    public void autoFase12(PrintWriter out, ArrayList<Album> albums){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<albums>");
        for (int cn=0; cn < albums.size(); cn++){
            out.println("<album year='"+albums.get(cn).getYear()+"' performer='"+albums.get(cn).getInterprete()+"' review='"+albums.get(cn).getReview()+"'>"+albums.get(cn).getName()+"</album>");
        }
        out.println("</albums>");
    }

    /* Screen en modo auto para la Fase 13 : Mostramos las songs de pop que pertenecen a un album dado mediante su identificador */
    public void autoFase13(PrintWriter out, ArrayList<Song> songs){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<songs>");
        for (int cn=0; cn < songs.size(); cn++){
            out.println("<song lang='"+songs.get(cn).getLang()+"' genres='"+songs.get(cn).getGenre()+"' composer='"+songs.get(cn).getComposer()+"'>"+songs.get(cn).getTitle()+"</song>");
        }
        out.println("</songs>");
    }

    /* Screen en modo browser para cuando el usario no introduce contraseña */
    public void doGetFaltaContraseña(PrintWriter out){
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'/><title>Falta contraseña</title>");
        out.println("<link rel='stylesheet' href='./p2/p2.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Introduce una contraseña</h1>");
        out.println("</body>");
        out.println("</html>");
    }

    /* Screen en modo browser para cuando la contraseña es errónea */
    public void doGetContraseñaErronea(PrintWriter out){
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'/><title>Contraseña Erronea</title>");
        out.println("<link rel='stylesheet' href='./p2/p2.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Contraseña Erronea</h1>");
        out.println("</body>");
        out.println("</html>");
    }

    /* Screen en modo browser para cuando falta algún parámetro para acceder a una screen */
    public void doGetFaltaParametro(PrintWriter out, String parametro){
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'/><title>Introduce Parámetro</title>");
        out.println("<link rel='stylesheet' href='./p2/p2.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Introduce el parametro "+parametro+"</h1>");
        out.println("</body>");
        out.println("</html>");
    }
    
    /* Screen en modo browser para la Fase 01 */
    public void doGetFase01(PrintWriter out, String contraseñaRecibida) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='./p2/p2.css'>");
        out.println("<title>Servicio de consulta musical</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h1>Bienvenido a este servicio</h1>");
        out.println("<br>");
        out.println("<h2>Selecciona una consulta:</h2>");
        out.println("<ul>");
        out.println("<li><a href=\"?p="+contraseñaRecibida+"&pphase=02\">Ver los ficheros erróneos</a></li>");
        out.println("<li><a href=\"?p="+contraseñaRecibida+"&pphase=11\">Consulta 1: Canciones pop de un Album de un Country</a></li>");
        out.println("</ul>");
        out.println("<br>");
        out.println("<hr>");
        out.println("<h4>Autor: Miguel Pastoriza Santaclara</h4>");
        out.println("</body>");
        out.println("</html>");

    }

    /* Screen en modo browser para la Fase 02: Mostramos los ficheros erróneos encontrados */
    public void doGetFase02(PrintWriter out,String contraseñaRecibida, TreeMap<String,String> fatalError,TreeMap<String,String> error){
             
        Set<Map.Entry<String, String>> entrySetError = error.entrySet();
        List<Map.Entry<String, String>> entryListError = new ArrayList<>(entrySetError);

        Set<Map.Entry<String, String>> entrySetFatalError = fatalError.entrySet();
        List<Map.Entry<String, String>> entryListFatalError = new ArrayList<>(entrySetFatalError);

        String enlace_xml = new String();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='./p2/p2.css'>");
        out.println("<title>Servicio de consulta musical</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Ficheros con errores: "+entryListError.size()+"</h2>");
        out.println("<ul>");

        for (int index = 0; index<entryListError.size(); index++){
            enlace_xml = entryListError.get(index).getValue();
            out.println("<li>"+enlace_xml+"</li>");
        }

        out.println("</ul>");
        out.println("<h2>Ficheros con errores fatales: "+entryListFatalError.size()+"</h2>");
        out.println("<ul>");
        
        for (int index = 0; index<entryListFatalError.size(); index++){
            enlace_xml = entryListFatalError.get(index).getValue();
            out.println("<li>"+enlace_xml+"</li>");
        }

        out.println("</ul>");
        out.println("<br>");    
        out.println("<button type=\"button\" id=\"atras\" onclick=\"location.href=\'?p="+contraseñaRecibida+"&pphase=01\';\">Atras</button>");
        out.println("<hr>");
        out.println("<h4>Autor: Miguel Pastoriza Santaclara</h4>");
        out.println("</body>");
        out.println("</html>");
        
    }

    /* Screen en modo browser para la Fase11 : Mostramos todos los países encontrados */
    public void doGetFase11(PrintWriter out, String contraseñaRecibida, ArrayList<String> countries){

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='./p2/p2.css'>");
        out.println("<title>Servicio de consulta musical</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 1: Fase 1</h2>");
        out.println("<h2>Selecciona una Country:</h2>");
        out.println("<ol>");

        for (int cn=0; cn < countries.size(); cn++){
            out.println("<li><p><a href='?p="+contraseñaRecibida+"&pphase=12&pcountry="+countries.get(cn)+"'>"+countries.get(cn)+"</a></p></li>");
        }
        
        out.println("</ol>");
        out.println("<br>");
        out.println("<br>");    
        out.println("<button type=\"button\" id=\"inicio\" onclick=\"location.href=\'?p="+contraseñaRecibida+"&pphase=01\';\">Inicio</button>");
        out.println("<hr>");
        out.println("<h4>Autor: Miguel Pastoriza Santaclara</h4>");
        out.println("</body>");
        out.println("</html>");

    }

    /* Screen en modo browser para la Fase12 : Mostramos todos los albumes dado una country */
    public void doGetFase12(PrintWriter out, String contraseñaRecibida, String country, ArrayList<Album> albums){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='./p2/p2.css'>");
        out.println("<title>Servicio de consulta musical</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 1 : Fase 2 (Country = "+albums.get(0).getCountry()+")</h2>");
        out.println("<h2>Selecciona un Album:</h2>");
        out.println("<ol>");

        for (int cn=0; cn < albums.size(); cn++){
            out.println("<li><p><a href='?p="+contraseñaRecibida+"&pphase=13&pcountry="+albums.get(cn).getCountry()+"&paid="+albums.get(cn).getAid()+"'>Álbum = "+albums.get(cn).getName()+"</a>  ---Año = '"+albums.get(cn).getYear()+"' ---Intérprete = '"+albums.get(cn).getInterprete()+"' ---Review ='"+albums.get(cn).getReview()+"'</p></li>");
        }
        
        out.println("</ol>");
        out.println("<br>");
        out.println("<br>");    
        out.println("<button type=\"button\" id=\"inicio\" onclick=\"location.href=\'?p="+contraseñaRecibida+"&pphase=01\';\">Inicio</button>");
        out.println("<button type=\"button\" id=\"atras\" onclick=\"location.href=\'?p="+contraseñaRecibida+"&pphase=11\';\">Atras</button>");
        out.println("<hr>");
        out.println("<h4>Autor: Miguel Pastoriza Santaclara</h4>");
        out.println("</body>");
        out.println("</html>");
    }

    /* Screen en modo browser para la Fase 13 : Mostramos las canciones de pop que hay en un album */
    public void doGetFase13(PrintWriter out, String contraseñaRecibida, String country, String aid, ArrayList<Song> songs){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='./p2/p2.css'>");
        out.println("<title>Servicio de consulta musical</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 1 : Fase 3 (Country = "+country+", Album = "+aid+")</h2>");
        out.println("<h2>Este es el resultado de la consulta:</h2>");
        out.println("<ol>");

        for (int i=0; i < songs.size(); i++){
            out.println("<li><p>---Título = '"+songs.get(i).getTitle()+"' ---Idioma = '"+songs.get(i).getLang()+"' ---Géneros ='"+songs.get(i).getGenre()+"' ---Compositor = '"+songs.get(i).getComposer()+"'</p></li>");
        }

        out.println("</ol>");
        out.println("<br>");
        out.println("<br>");    
        out.println("<button type=\"button\" id=\"inicio\" onclick=\"location.href=\'?p="+contraseñaRecibida+"&pphase=01\';\">Inicio</button>");
        out.println("<button type=\"button\" id=\"atras\" onclick=\"location.href=\'?p="+contraseñaRecibida+"&pphase=12&pcountry="+country+"\';\">Atras</button>");
        out.println("<hr>");
        out.println("<h4>Autor: Miguel Pastoriza Santaclara</h4>");
        out.println("</body>");
        out.println("</html>");
    }
    
}
