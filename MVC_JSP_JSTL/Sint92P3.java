package p3;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.util.*;
import p3.DataModel.*;


public class Sint92P3 extends HttpServlet {

    
    private final static String contraseña = "SINT3migui";

    private final static String documento_inicial = "http://alberto.gil.webs.uvigo.es/SINT/22-23/muml2001.xml";

    DataModel modelo = new DataModel();
    

    public void init(){
        try{
            modelo.parser(documento_inicial); //Pasamos al parser el documento incial
        } catch(Exception e){
            e.printStackTrace();
        }
    }
   
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        RequestDispatcher rd;

        String contraseñaRecibida = request.getParameter("p");
        String country = request.getParameter("pcountry");
        String aid = request.getParameter("paid");
        String parametro = new String();

        Fase01Bean fase01Bean = new Fase01Bean();
        Fase02Bean fase02Bean = new Fase02Bean();
        Fase11Bean fase11Bean = new Fase11Bean();
        Fase12Bean fase12Bean = new Fase12Bean();
        Fase13Bean fase13Bean = new Fase13Bean();

        try{
            /* En el caso de que no introduce contraseña */
            if (contraseñaRecibida == null){ 
                rd = sc.getRequestDispatcher("/FaltaContraseña.jsp");
                rd.forward(request,response);          
            /* Contraseña erronea */
            } else if (contraseñaRecibida.equals(contraseña) == false ){ 
                rd = sc.getRequestDispatcher("/ContraseñaErronea.jsp");
                rd.forward(request,response);  
            /* No ha puesto fase o ha puesto la inicial (Fase01) */
            } else if ((request.getParameter("pphase") == null) || (request.getParameter("pphase").equals("01") == true)){
                fase01Bean.setPassword(contraseñaRecibida); //Llamamos al bean correspondiente
                request.setAttribute("fase01Bean",fase01Bean);
                rd = sc.getRequestDispatcher("/Fase01.jsp");
                rd.forward(request,response);
            /* Fase 02 */
            } else if ((request.getParameter("pphase").equals("02") == true)){
                fase02Bean.setPassword(contraseñaRecibida);
                request.setAttribute("fase02Bean",fase02Bean); //Llamamos al bean correspondiente
                rd = sc.getRequestDispatcher("/Fase02.jsp"); //Mostramos la screen 
                rd.forward(request,response);
            /* Fase 11 */
            } else if ((request.getParameter("pphase").equals("11") == true)){
                fase11Bean.setPassword(contraseñaRecibida);
                request.setAttribute("fase11Bean",fase11Bean); //Llamamos al bean correspondiente
                rd = sc.getRequestDispatcher("/Fase11.jsp"); //Mostramos la screen 
                rd.forward(request,response);
            /* Fase 12 */
            } else if ((request.getParameter("pphase").equals("12") == true)){
                if (request.getParameter("pcountry") == null){ //No intrdouce el parametro pais para buscar sus albumes
                    parametro="pcountry";
                    request.setAttribute("parametro",parametro);
                    rd = sc.getRequestDispatcher("/FaltaParametro.jsp");
                    rd.forward(request,response);
                } else{
                    fase12Bean.setCountry(request.getParameter("pcountry"));
                    fase12Bean.setPassword(contraseñaRecibida);
                    request.setAttribute("fase12Bean",fase12Bean); //Llamamos al bean correspondiente
                    rd = sc.getRequestDispatcher("/Fase12.jsp"); //Mostramos la screen 
                    rd.forward(request,response);
                }

            /* Fase 13 */
            } else if ((request.getParameter("pphase").equals("13") == true)){
                if (request.getParameter("pcountry") == null){ //No intrdouce el parametro pais para buscar sus albumes
                    parametro="pcountry";
                    request.setAttribute("parametro",parametro);
                    rd = sc.getRequestDispatcher("/FaltaParametro.jsp"); //Mostramos la screen 
                    rd.forward(request,response);
                } else if (request.getParameter("paid") == null){
                    parametro="paid";
                    request.setAttribute("parametro",parametro);
                    rd = sc.getRequestDispatcher("/FaltaParametro.jsp"); //Mostramos la screen 
                    rd.forward(request,response);
                } else{
                    fase13Bean.setCountry(request.getParameter("pcountry"));
                    fase13Bean.setAid(request.getParameter("paid"));
                    fase13Bean.setPassword(contraseñaRecibida);
                    request.setAttribute("fase13Bean",fase13Bean); //Llamamos al bean correspondiente
                    rd = sc.getRequestDispatcher("/Fase13.jsp"); //Mostramos la screen 
                    rd.forward(request,response);
                }
            }
        } catch(Exception e){

        }
    }
}
    