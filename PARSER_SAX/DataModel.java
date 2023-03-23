package p4;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class DataModel {

    /* Mapas para guardar los archivos xml */
    public static TreeMap<Integer,String> documentosCorrectos = new TreeMap<Integer,String>(); //<Year,enlace_xml>
    public static TreeMap<String,String> documentosPendientes = new TreeMap<String,String>(); //<nombre_xml,enlace_xml>
    public static TreeMap<String,String> fatalError = new TreeMap<String,String>(); //<nombre_xml,enlace_xml>
    public static TreeMap<String,String> error = new TreeMap<String,String>(); //<nombre_xml,enlace_xml>
    
    static ArrayList<String> paisesArray = new ArrayList<String>();
    static ArrayList<Album> albumsTodos = new ArrayList<Album>();

    static String nombre_xml = new String();
    static String documento_xml = new String();
    static String url_xml = "http://alberto.gil.webs.uvigo.es/SINT/22-23/"; //url por la que empiezan todos los xml
        
    /* 
    parser (String documento_xml) : Método para buscar todos los archivos muml a partir de uno dado, 
    la finalidad sería ir guardando cada archivo en el mapa correspondiente :
       - Correcto 
       - FatalError (no wellformed)
       - Error (fuera de los años preestablecidos) 
    */
    public static void parser(String documento_inicial){
        
        documentosPendientes.put("Inicial",documento_inicial); //Añadimos el primer documento a documentos pendientes
        boolean wellformed = true;

        try{
            //Creamos el Sax parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            Handler handler = new Handler(); //Nueva clase Handler

            while(!documentosPendientes.isEmpty()) { //Bucle hasta que el mapa de documentos pendientes esté vacío

                Set<Map.Entry<String, String>> entrySet = documentosPendientes.entrySet();
                List<Map.Entry<String, String> > entryList = new ArrayList<>(entrySet);
                nombre_xml = entryList.get(0).getKey();  //Leemos el año del documento
                documento_xml = entryList.get(0).getValue(); //Leemos  el enlace xml del documento
                    
                URL enlace = new URL(documento_xml); 
                InputStream inputStream = enlace.openStream();
                Reader reader = new InputStreamReader(inputStream, "UTF-8");
                InputSource is = new InputSource(reader);
                is.setEncoding("UTF-8");

                try{
                    saxParser.parse(is, handler); // Abrirmos el archivo xml
                    wellformed = true; // Si no da ningún error está well formed
                }
                catch(Exception e){ 
                    /* En el caso de que salte alguna excepción sería un fatalError*/
                    fatalError.put(nombre_xml,documento_xml); // Lo añadimos al mapa de los documentos fatal error
                    wellformed = false; 
                }    
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
             
    /*
        Clase Handler para parsear los archivos xml
    */
    public static class Handler extends DefaultHandler{

        boolean anho = false;
        boolean pais = false;
        boolean album = false;
        boolean interprete = false;
        boolean nombre = false;
        boolean ISBN = false;
        boolean companhia = false;
        boolean cancion = false;
        boolean titulo = false;
        boolean duracion = false;
        boolean genero = false;
        boolean compositor = false;
        boolean muml = false;
        boolean noVacia = false;

        Album nuevoAlbum = new Album();
        Song nuevaCancion = new Song();
        ArrayList<Song> cancionesArray = new ArrayList<Song>();
        ArrayList<String> generosArray = new ArrayList<String>();

        int anhoInteger;
        String anhoString = new String();
        String nombreString = new String();
        String paisString = new String();
        String interpreteString = new String();
        String aidString = new String();
        String formatoString = new String();
        String ISBNString = new String();
        String companhiaString = new String();
        String reviewString = new String();
        String sidString = new String();
        String idiomaString = new String();
        String tituloString = new String();
        String duracionString = new String();
        String generosString = new String();
        String compositorString = new String();
        String mumlString = new String();

        String randomText = new String();
        String randomText2 = new String();

        /* Método startElement, busca el inicio de los tags en un archivo xml */
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            
            if (qName.equalsIgnoreCase("Year")) {
                anho = true; //Si encuentra el tag Year ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Country")) {
                pais = true; //Si encuentra el tag Country ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Album")) {
                cancionesArray = new ArrayList<Song>(); //Por cada album creamos un ArrayList con las canciones contenidas
                aidString = attributes.getValue("aid"); //Guardamos el aid del album
                formatoString = attributes.getValue("format"); //Guardamos el formato
                album = true; //Si encuentra el tag Album ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Name")) {
                nombre = true; //Si encuentra el tag Name ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Singer") || qName.equalsIgnoreCase("Group")) {
                interprete = true; //Tanto si encuentra Singer como Group ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("ISBN")) {
                ISBN = true; //Si encuentra el tag ISBN ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Company")) {
                companhia = true; //Si encuentra el tag Company ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Song")) {
                generosArray = new ArrayList<String>(); //Por cada song creamos un ArrayList con los géneros
                sidString = attributes.getValue("sid"); //Guardamos el sid de la canción
                idiomaString = attributes.getValue("lang"); //Guardamos el idioma de la canción
                cancion = true; //Si encuentra el tag Song ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Title")) {
                titulo = true; //Si encuentra el tag Title ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Duration")) {
                duracion = true; //Si encuentra el tag Duration ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Genre")) {
                genero = true; //Si encuentra el tag Genre ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Composer")) {
                compositor = true; //Si encuentra el tag Composer ponemos a true para posteriormente leer su valor
            }
            if (qName.equalsIgnoreCase("Muml")) {
                muml = true; //Si encuentra el tag Muml ponemos a true para posteriormente leer su valor
            }
            if (qName.length()!=0){
                noVacia = true; //Lo utilizamos para no posteriormente buscar la review sin tener todos los datos del xml por el medio
            }
        }

        /* Método endElement que usamos para saber cuando la información de un nodo ha acabado */
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("Album")) { //Una vez acabe Álbum
                if(documentosCorrectos.containsKey(anhoInteger)){ //Si el mapa de documentos correctos contiene a este álbum
                    nuevoAlbum = new Album(nombreString,paisString,interpreteString,ISBNString,companhiaString,aidString,formatoString,anhoInteger,reviewString,cancionesArray); //Guardamos la información del nuevo álbum
                    albumsTodos.add(nuevoAlbum); //Añadimos el nuevo álbum al ArrayList con todos los álbumes
                }
            }
            if (qName.equalsIgnoreCase("Song")) { //Una vez acabe una canción
                String generoArray[] = new String[generosArray.size()];
                for(int i=0;i<generosArray.size();i++){   
                    generoArray[i] = generosArray.get(i); //Guardamos los géneros en una Array (el mapa es más comodo para añadir nuevos elementos de forma dinámica)
                }
                if(generosArray.contains("Pop")== true){ //En el caso de que la canción tenga como mínimo el género pop
                    nuevaCancion = new Song(tituloString,duracionString,generoArray,compositorString,mumlString,sidString,idiomaString); //Creamos un objeto Song con sus valores
                    cancionesArray.add(nuevaCancion); //Añadimos la canción al Arraylist con las canciones de ese álbum
                }
            }
            if (qName.equalsIgnoreCase("Year")) { //UNa vez acabe de leer el año
                if(anhoInteger<=1980 || anhoInteger >=2021){
                    error.put(nombre_xml,documento_xml); //Si no está en esos años lo añadimos al mapa con los documentos erróneos
                    documentosPendientes.remove(documentosPendientes.firstKey()); //Eliminamos el archivo xml analizado de la pila de documentos pendientes

                } else{ //Esta en el rango de años permitido
                    documentosPendientes.remove(documentosPendientes.firstKey()); //Eliminamos el archivo xml analizado de la pila de documentos pendientes
                    documentosCorrectos.put(anhoInteger,documento_xml); //Lo añadimos al mapa con los documentos correctos
                }
            }
            if (qName.equalsIgnoreCase("Muml")) { //Una vez acabe de leer los muml internos
                if(documentosCorrectos.containsKey(anhoInteger)){ //Si el fichero que estamos leyendo pertenece al mapa de documentos correctos
                    documento_xml = url_xml+mumlString;
                    documentosPendientes.put(mumlString,documento_xml); //Añadimos los muml nuevos encontrados al mapa de documentos pendientes
                } //Cuando vaya a leer los muml internos ya estará añadido el archivo xml al que pertenecen al mapa de documentos correctos si es el caso
            }
        }

        /* Método characters para guardar la información de cada tag en el caso de que esté true */

        public void characters(char ch[], int start, int length) throws SAXException {
        
            if (anho) {
                anhoString = new String(ch, start, length);   //Guardamos el año en una string
                anhoInteger = Integer.parseInt(anhoString);    //Lo pasamos a int
                anho = false; //Reiniciamos
            }
            if (pais) {
                paisString = new String(ch, start, length); //Guardamos el país enconentrado        
                if(!paisesArray.contains(paisString)){ //En el caso de que no esté ya guardado
                    paisesArray.add(paisString);   //Añadimos un pais nuevo
                } 
                pais = false; //Reiniciamos
            }
            if (album) {
                randomText = new String(ch, start, length); //Buscamos el texto que haya por álbum para encontrar la review
                randomText = randomText.trim();     
                if(randomText.length()!=0){
                    reviewString = new String(randomText); //En el caso de que se encuentre algo será la review del álbum
                }
                album = false; //Reiniciamos
            }
            if(nombre){
                nombreString = new String(ch, start, length); //Guardamos el nombre del álbum
                nombre = false;  //Reiniciamos
            }
            if (interprete) {
                interpreteString = new String(ch, start, length); //Guardamos el intérprete
                interprete = false; //Reiniciamos
            }
            if (ISBN) {
                ISBNString = new String(ch, start, length); //Guardamos el ISBN
                ISBN = false; //Reiniciamos
            }
            if (companhia) {
                companhiaString = new String(ch, start, length); //Guardamos la compañía
                companhia = false; //Reiniciamos
            }
            if(cancion){
                cancion = false; //Reiniciamos
            }
            if (titulo) {
                tituloString = new String(ch, start, length); //Guardamos el título de la Song
                titulo = false; //Reiniciamos
            }
            if (duracion) {
                duracionString = new String(ch, start, length); //Guardamos la duración
                duracion = false; //Reiniciamos
            }
            if (genero) {
                generosString = new String(ch, start, length); //Guardamos los géneros
                generosArray.add(generosString);
                genero = false; //Reiniciamos
            }
            if (compositor) {
                compositorString = new String(ch, start, length); //Guardamos el compositor
                compositor = false; //Reiniciamos
            }
            if (muml) {
                mumlString = new String(ch, start, length); //Guardamos el nombre de los posibles siguientes muml
                muml = false; //Reiniciamos
            }
            if (noVacia) {
                noVacia = false; //Reiniciamos                         
            }
            else {
                randomText2 = new String(ch, start, length); //En el caso de que no se haya encontrado review ya será ese texto no registrado
                randomText2 = randomText2.trim();
                if(randomText2.length()!=0){
                    reviewString = new String(randomText2);
                }
            }
        }
                
    }
    
    /* 
    getQ1Countries() : Método para leer todos los paises almacenados,
    devolvemos un ArrayList<String> con los países en orden alfabético inverso
    */
    static ArrayList<String> getQ1Countries() {
        Collections.sort(paisesArray,Collections.reverseOrder());     //Orden inverso          
        return paisesArray; //Devolvemos el ArrayList con los paises
    }

    /* 
    getQ1Albums(String country) : Método para leer todos los albumes dado un país, retornamos un ArrayList<Album> con los albumes encontrados
    */
    static ArrayList<Album> getQ1Albums(String pais){

        ArrayList<Album> albums = new ArrayList<Album>();

        for(int i=0;i<albumsTodos.size();i++){
            if(albumsTodos.get(i).getCountry().equals(pais)== true){ //Buscamos todos los álbumes que pertenezcan a un pais
                albums.add(albumsTodos.get(i));
            }
        }
        Collections.sort(albums); //Ordenamos los albumes, primero por año, luego por orden alfabético si hicera falta
        return albums; //Retornamos el ArrayList con los albumes
    }

    /*
    getQ1Songs(String country, String album) : Método para encontrar las canciones que pertenecen a un país y a un aid,
    retornamos un ArrayList<Song> con las canciones encontradas.
    */
    static ArrayList<Song> getQ1Songs(String pais, String aid){
        ArrayList<Song> canciones= new ArrayList<Song>();
        Song nuevaCancion = new Song();

        for(int i=0;i<albumsTodos.size();i++){ //Buscamos las canciones en todos los álbumes
            if(albumsTodos.get(i).getCountry().equals(pais)== true && albumsTodos.get(i).getAid().equals(aid)== true ){ //Filtramos por país y aid
                for(int j=0;j<albumsTodos.get(i).getSongs().size();j++){
                    nuevaCancion = albumsTodos.get(i).getSong(j); //En el caso de que esa canción esté en el álbum correcto la guardamos
                    canciones.add(nuevaCancion);
                }   
            }
        }
        Collections.sort(canciones); //Ordenamos las canciones por el número de Géneros a los que pertenece, posteriormente por orden alfabético si hiciera falta (mismo número de géneros)
        return canciones;
    }

    /*
    getFatalError() : Devolvemos el mapa con los documentos fatalError
    */
    static TreeMap<String,String> getFatalError(){
        return fatalError;
    } 

    /*
    getEror() : Devolvemos el mapa con los documentos error
    */
    static TreeMap<String,String> getError(){
        return error;
    }
    
}
