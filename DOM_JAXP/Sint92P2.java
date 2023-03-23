package p2;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.util.*;
import p2.DataModel.*;

public class Sint92P2 extends HttpServlet {

    
    private final static String contraseña = "SINT2migui";

    private final static String documento_inicial = "http://alberto.gil.webs.uvigo.es/SINT/22-23/muml2001.xml";
   
    DataModel modelo = new DataModel();

    /* 
    init (ServletConfig servletconf) : Método inicial para parsear el documento inicial dado
    */
    public void init(ServletConfig servletconf){
        try{
            modelo.parser(documento_inicial); //Pasamos al parser el documento incial
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    doGet(HttpServletRequest req, HttpServletResponse res) : Método para mostrar las distintas screens según el usuario desee
    */
    public void doGet(HttpServletRequest req, HttpServletResponse res){ 

        TreeMap<String,String> fatalError = modelo.getFatalError();
        TreeMap<String,String> error = modelo.getError();

        try {
            
            res.setCharacterEncoding("utf-8");
            
            PrintWriter out = res.getWriter(); //Nos permite imprimir en pantalla
            FrontEnd frontend = new FrontEnd(); 
            
            String contraseñaRecibida = req.getParameter("p");
            String country = req.getParameter("pcountry");
            String aid = req.getParameter("paid");

            /* En el caso de que no introduce contraseña */
            if (req.getParameter("p") == null){ 
                if((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){ //Modo auto activado
                    frontend.autoFaltaContraseña(out); //Screen en modo auto
                }else{ //Modo browser
                    frontend.doGetFaltaContraseña(out); //Screen en modo browser
                }    

             /* Contraseña erronea */
            } else if (req.getParameter("p").equals(contraseña) == false ){ 
                if((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){ //Modo auto activado
                    frontend.autoContraseñaErronea(out); //Screen en modo auto
                }else{ //Modo browser
                    frontend.doGetContraseñaErronea(out); //Screen en modo browser
                }    
            
            /* No ha puesto fase o ha puesto la inicial (Fase01) */
            } else if ((req.getParameter("pphase") == null) || (req.getParameter("pphase").equals("01") == true)){
                if((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){ //Modo auto activado
                    frontend.autoFase01(out); //Screen en modo auto
                }else{ //Modo browser
                    frontend.doGetFase01(out,contraseñaRecibida);//Screen en modo browser
                } 
                
            /* FASE 02 */
            } else if ((req.getParameter("pphase") != null) && (req.getParameter("pphase").equals("02") == true)){
                if((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){ //Modo auto activado
                    frontend.autoFase02(out,fatalError, error); //Screen en modo auto, le pasamos el mapa con los errores
                }else{ //Modo browser
                    frontend.doGetFase02(out,contraseñaRecibida,fatalError, error);//Screen en modo browser, le pasamos el mapa con los errores
                } 
                
            /* FASE 11 */
            } else if ((req.getParameter("pphase") != null) && (req.getParameter("pphase").equals("11") == true)){
                
                ArrayList<String> countries = modelo.getQ1Countries(); //Obtenemos el arraylist con los countries
                if((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){ //Modo auto activado
                    frontend.autoFase11(out,countries);//Screen en modo auto, le pasamos el arraylist de los paises que tenemos
                }else{ //Modo browser
                    frontend.doGetFase11(out,contraseñaRecibida,countries); //Screen en modo browser, le pasamos el arraylist de los paises que tenemos
                } 

            /* FASE 12 */ 
            } else if ((req.getParameter("pphase") != null) && (req.getParameter("pphase").equals("12") == true)){
 
                if (req.getParameter("pcountry") == null){ //No intrdouce el parametro pais para buscar sus albumes
                    String parametro="pcountry";
                    if((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){ //Modo auto activado
                        frontend.autoFaltaParametro(out,parametro);//Screen en modo auto
                    }else{ //Modo browser
                        frontend.doGetFaltaParametro(out,parametro); //Screen en modo browser
                    } 
                    
                } else{
                    country = req.getParameter("pcountry");
                    ArrayList<Album> albums = modelo.getQ1Albums(country); //Obtenemos los albumes según el país

                    if((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){ //Modo auto activado
                        frontend.autoFase12(out,albums); //Screen en modo auto
                    }else{ //Modo browser
                        frontend.doGetFase12(out,contraseñaRecibida,country,albums); //Screen en modo browser
                    } 
                }

            /* FASE 13 */
            } else if ((req.getParameter("pphase") != null) && (req.getParameter("pphase").equals("13") == true)){

                if (req.getParameter("pcountry") == null){ //Falta el parámetro pcrountry
                    String parametro="pcountry";
                    if((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){ //Modo auto activado
                        frontend.autoFaltaParametro(out,parametro);//Screen en modo auto
                    }else{ //Modo browser
                        frontend.doGetFaltaParametro(out,parametro); //Screen en modo browser
                    } 
                }else if(req.getParameter("paid") == null){ // Falta el parámetro paid
                    String parametro="paid";
                    if((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){ //Modo auto activado
                        frontend.autoFaltaParametro(out,parametro);//Screen en modo auto
                    }else{ //Modo browser
                        frontend.doGetFaltaParametro(out,parametro); //Screen en modo browser
                    } 
                }else{
                    country = req.getParameter("pcountry");
                    aid = req.getParameter("paid");

                    ArrayList<Song> songs = modelo.getQ1Songs(country,aid);
                    if((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){ //Modo auto activado
                        frontend.autoFase13(out,songs);//Screen en modo auto
                    }else{ //Modo browser
                        frontend.doGetFase13(out,contraseñaRecibida,country,aid,songs); //Screen en modo browser
                    } 
                    
                }
            }
        } catch (Exception ex) {
            System.out.println("Algo fue mal en la ejecución del servlet: "+ex.toString());
        }
    }
}