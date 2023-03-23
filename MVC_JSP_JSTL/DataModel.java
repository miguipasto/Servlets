package p3;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

import p3.*;

public class DataModel {


    /* Mapas para guardar los archivos xml */
    public static TreeMap<Integer,Document> documentosCorrectos = new TreeMap<Integer,Document>(); //<Year,Document>
    public static TreeMap<String,String> documentosPendientes = new TreeMap<String,String>(); //<nombre_xml,enlace_xml>
    public static TreeMap<String,String> fatalError = new TreeMap<String,String>(); //<nombre_xml,enlace_xml>
    public static TreeMap<String,String> error = new TreeMap<String,String>(); //<nombre_xml,enlace_xml>

    
    /* 
    parser (String documento_xml) : Método para buscar todos los archivos muml a partir de uno dado, 
    la finalidad sería ir guardando cada archivo en el mapa correspondiente :
       - Correcto 
       - FatalError (no wellformed)
       - Error (fuera de los años preestablecidos) 
    */

    public static void parser(String documento_inicial) throws Exception,SAXParseException, XPathExpressionException{

        String url_xml = "http://alberto.gil.webs.uvigo.es/SINT/22-23/"; //url por la que empiezan todos los xml

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        
        Document documento = null;

        NodeList nodeYear = null;
        Element elemYear = null;
        String stringYear = new String();

        String documento_xml= new String();

        //Solo para el primero
        documento_xml = documento_inicial;
        documentosPendientes.put("Inicial",documento_xml);

        while(!documentosPendientes.isEmpty()) { //Bucle hasta que el mapa de documentos pendientes esté vacío

            boolean wellformed = true;

            Set<Map.Entry<String, String>> entrySet = documentosPendientes.entrySet();
            List<Map.Entry<String, String> > entryList = new ArrayList<>(entrySet);
            String nombre_xml = entryList.get(0).getKey();
            documento_xml = entryList.get(0).getValue();

            try{
                documento = builder.parse(documento_xml); // Abrirmos el archivo xml
                wellformed = true; // Si no da ningún error está well formed
            }
            catch(Exception e){ 
                /* En el caso de que salte alguna excepción sería un fatalError*/
                documentosPendientes.remove(documentosPendientes.firstKey()); //Eliminamos el archivo xml analizado de la pila de documentos pendientes
                fatalError.put(nombre_xml,documento_xml); // Lo añadimos al mapa de los documentos fatal error
                wellformed = false; 
            }

            if(wellformed){ //Si el documento es well-formed
 
                //Vemos si esta en el year correcto
                nodeYear = (NodeList)xpath.evaluate("//Year", documento, XPathConstants.NODESET);
                elemYear = (Element)nodeYear.item(0);
                stringYear = elemYear.getTextContent().trim();
                Integer year = Integer.parseInt(stringYear);

                if(year<=1980 || year >=2021){
                    error.put(nombre_xml,documento_xml); //Si no está en esos años lo añadimos al mapa con los documentos erróneos
                    documentosPendientes.remove(documentosPendientes.firstKey()); //Eliminamos el archivo xml analizado de la pila de documentos pendientes

                } else{ //Esta en el rango de años permitido

                    documentosPendientes.remove(documentosPendientes.firstKey()); //Eliminamos el archivo xml analizado de la pila de documentos pendientes
                    documentosCorrectos.put(year,documento); //Lo añadimos al mapa con los documentos correctos

                    //Leemos los MuML que tiene almacenados dentro
                    NodeList nodeMuml = (NodeList)xpath.evaluate("//MuML", documento, XPathConstants.NODESET);
                    String nextMuml = new String();
                    
                    for (int co=0; co < nodeMuml.getLength(); co++)  {
                        Element elemMuml = (Element)nodeMuml.item(co);
                        nextMuml = elemMuml.getTextContent().trim(); //Sacamos el nombre del siguiente xml
                        documento_xml = url_xml+nextMuml; //Creamos el enlace añadiendo el nombre_xml a la url

                        //Guardamos en documentos pendientes los nuevos
                        documentosPendientes.put(nextMuml,documento_xml);

                    }
                }   
            }     
        } 
    }

    
    /*
    getFatalError() : Devolvemos una ArrayList con los documentos fatalError
    */
    static ArrayList<String> getFatalError(){

        Set<Map.Entry<String, String>> entrySetFatalError = fatalError.entrySet();
        List<Map.Entry<String, String>> entryListFatalError = new ArrayList<>(entrySetFatalError);

        String nombre_xml = new String();
        ArrayList<String> fatalErrorArray = new ArrayList<String>();

        for (int index = 0; index<entryListFatalError.size(); index++){
            nombre_xml = entryListFatalError.get(index).getValue();
            fatalErrorArray.add(nombre_xml);
        }
        return fatalErrorArray;
    } 

    /*
    getError() : Devolvemos una ArrayList con los documentos error
    */
    static ArrayList<String> getError(){
        Set<Map.Entry<String, String>> entrySetError = error.entrySet();
        List<Map.Entry<String, String>> entryListError = new ArrayList<>(entrySetError);

        String nombre_xml = new String();
        ArrayList<String> ErrorArray = new ArrayList<String>();

        for (int index = 0; index<entryListError.size(); index++){
            nombre_xml = entryListError.get(index).getValue();
            ErrorArray.add(nombre_xml);
        }
        return ErrorArray;
    } 


    /* 
    getQ1Countries() : Método para leer todos los countries almacenados en los archivos muml correctos,
    devolvemos un ArrayList<String> con los países en orden alfabético inverso
    */
    static ArrayList<String> getQ1Countries() throws ParserConfigurationException, XPathExpressionException{
        
        ArrayList<String> countries = new ArrayList<String>();

        //Variables para leer mediante xpath
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        String xpathTarget = "/Music/Album/Country"; //Ruta para buscar los países

        Iterator<Integer> iteratorDocumentos = documentosCorrectos.keySet().iterator();

        while (iteratorDocumentos.hasNext()){
            
            Integer year = iteratorDocumentos.next(); // Leemos el año desde el Mapa de documentos correctos
            Document documento = documentosCorrectos.get(year);

            NodeList nlCountryNames = (NodeList)xpath.evaluate(xpathTarget, documento, XPathConstants.NODESET); //Buscamos el numero de países que hay en un archivo xml

            for (int co=0; co < nlCountryNames.getLength(); co++)  {
                Element elemCountryName = (Element)nlCountryNames.item(co); 
                String countryName = elemCountryName.getTextContent().trim(); //Leemos el pais
                //Nos aseguramos de que no esté repetido en la ArrayList
                if(!countries.contains(countryName)){ 
                    countries.add(countryName);   //Añadimos un pais nuevo
                }  
            }  
        }       

        Collections.sort(countries,Collections.reverseOrder());     //Orden inverso          
        return countries; //Devolvemos el ArrayList con los paises

    }

    /* 
    getQ1Albums(String country) : Método para leer todos los albumes dado un país, retornamos un ArrayList<Album> con los albumes encontrados
    */
    static ArrayList<Album> getQ1Albums (String country) throws ParserConfigurationException,XPathExpressionException{
        
        ArrayList<Album> albums = new ArrayList<Album>();
        
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        String xpathTarget = "/Music/Album[Country='"+country+"']"; //Ruta para buscar los países

        Iterator<Integer> iteratorDocumentos = documentosCorrectos.keySet().iterator();

        while (iteratorDocumentos.hasNext()){
            
            Integer year = iteratorDocumentos.next(); // Leemos el año desde el Mapa de documentos correctos
            Document documento = documentosCorrectos.get(year);

            NodeList nlAlbum = (NodeList)xpath.evaluate(xpathTarget, documento, XPathConstants.NODESET); //Guardamos el número de albumes encontrados

            for (int co=0; co < nlAlbum.getLength(); co++)  {
                Album newAlbum = new Album();
                //Guardamos el año del álbum
                newAlbum.setYear(year); 

                Element elemAlbum = (Element)nlAlbum.item(co);

                //Guardamos el nombre del álbum
                NodeList album_name = elemAlbum.getElementsByTagName("Name");
                Element elemAlbum_name = (Element)album_name.item(0);
                String albumName = elemAlbum_name.getTextContent().trim();
                newAlbum.setName(albumName); 

                //Guardamos el país del álbum
                NodeList album_country = elemAlbum.getElementsByTagName("Country");
                Element elemAlbum_country = (Element)album_country.item(0);
                String albumCountry = elemAlbum_country.getTextContent().trim();
                newAlbum.setCountry(albumCountry); 

                //Guardamos el interprete del álbum (singer o group)
                NodeList album_singer = elemAlbum.getElementsByTagName("Singer");
                NodeList album_group = elemAlbum.getElementsByTagName("Group");
                String albumInterprete = new String();
                
                try{
                    Element elemAlbum_singer = (Element)album_singer.item(0);
                    albumInterprete = elemAlbum_singer.getTextContent().trim();
                } catch (Exception e){
                    Element elemAlbum_group = (Element)album_group.item(0);
                    albumInterprete = elemAlbum_group.getTextContent().trim();
                }
                newAlbum.setInterprete(albumInterprete); 

                //Guardamos el ISBN del álbum
                NodeList album_ISBN = elemAlbum.getElementsByTagName("ISBN");
                Element elemAlbum_ISBN = (Element)album_ISBN.item(0);
                String albumISBN = elemAlbum_ISBN.getTextContent().trim();
                newAlbum.setISBN(albumISBN); 

                //Guardamos la compañia del álbum (es opcional)

                NodeList album_company = elemAlbum.getElementsByTagName("Company");
                String albumCompany = new String();
                try{
                    Element elemAlbum_company = (Element)album_company.item(0);
                    albumCompany = elemAlbum_company.getTextContent().trim();
                } catch (Exception e){
                    //No hacemos nada, dejamos la company null
                }
                newAlbum.setCompany(albumCompany); 

                //Guardamos el atributo aid
                String albumAid = elemAlbum.getAttribute("aid").trim();
                newAlbum.setAid(albumAid); 

                //Guardamos el atributo format (opcional)
                String albumFormat = elemAlbum.getAttribute("format").trim();
                newAlbum.setFormat(albumFormat); 

                //Buscamos texto suelto, correspondiente a la review del álbum (no tiene tag)
                String randomText = (String) xpath.evaluate("text()[normalize-space()]", nlAlbum.item(co), XPathConstants.STRING); 
                String albumReview = randomText.trim();
                newAlbum.setReview(albumReview); 

                albums.add(newAlbum);  
            }
        }

        Collections.sort(albums); //Ordenamos los albumes, primero por año, luego por orden alfabético si hicera falta
        return albums; //Retornamos el ArrayList con los albumes
    }

    /*
    getQ1Songs(String country, String album) : Leemos todas las canciones de un muml según el país y el aid del álbum,
    retornamos un ArrayList<Song> con las canciones encontradas.
    */
    static ArrayList<Song> getQ1Songs(String country, String aid) throws ParserConfigurationException, XPathExpressionException{
        ArrayList<Song> songs = new ArrayList<Song>();

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        String xpathTarget = "/Music/Album[Country='"+country+"'][@aid='"+aid+"']/Song[Genre='Pop']"; //Ruta para buscar los países
        
        Iterator<Integer> iteratorDocumentos = documentosCorrectos.keySet().iterator();

        while (iteratorDocumentos.hasNext()){
            
            Integer year = iteratorDocumentos.next(); // Leemos el año desde el Mapa de documentos correctos
            Document documento = documentosCorrectos.get(year);
    
            NodeList nlSong = (NodeList)xpath.evaluate(xpathTarget, documento, XPathConstants.NODESET); //Guardamos el numero de canciones encontradas en el documento

            for (int co=0; co < nlSong.getLength(); co++)  {
                Element elemSong = (Element)nlSong.item(co);

                Song newSong = new Song();

                //Guardamos el titulo de la song
                NodeList song_title = elemSong.getElementsByTagName("Title");
                Element elemSong_title = (Element)song_title.item(0);
                String songTitle = elemSong_title.getTextContent().trim();
                newSong.setTitle(songTitle);

                //Guardamos la duracion de la song
                NodeList song_duration= elemSong.getElementsByTagName("Duration");
                Element elemSong_duration= (Element)song_duration.item(0);
                String songDuration = elemSong_duration.getTextContent().trim();
                newSong.setDuration(songDuration);

                //Guardamos los generos de la song
                NodeList song_genre= elemSong.getElementsByTagName("Genre");
                String songGenreArray[] = new String[song_genre.getLength()];
                
                for (int i = 0; i< song_genre.getLength(); i++){
                    Element elemSong_genre= (Element)song_genre.item(i);
                    songGenreArray[i]= elemSong_genre.getTextContent().trim();
                }
                newSong.setGenres(songGenreArray);

                //Guardamos el compositor
                NodeList song_compose= elemSong.getElementsByTagName("Composer");
                Element elemSong_composer= (Element)song_compose.item(0);
                String songComposer = elemSong_composer.getTextContent().trim();
                newSong.setComposer(songComposer);

                //Guardamos el MuML
                String songMuML= new String();
                try{
                    NodeList song_muml= elemSong.getElementsByTagName("MuML");
                    Element elemSong_muml= (Element)song_muml.item(0);
                    songMuML = elemSong_muml.getTextContent().trim();
                } catch (Exception e){ }
                newSong.setMuml(songMuML);

                //Guardamos el atributo sid
                String songSid = elemSong.getAttribute("sid").trim();
                newSong.setSid(songSid);

                //Guardamos el atributo lang
                String songLang = elemSong.getAttribute("lang").trim();
                newSong.setLang(songLang);

                songs.add(newSong); 

            }
        }

        Collections.sort(songs); //Ordenamos las canciones por el número de Géneros a los que pertenece, posteriormente por orden alfabético si hiciera falta (mismo número de géneros)
        return songs;
    }

}