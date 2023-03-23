package p3;

import p3.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

/*
    Bean para mostrar la Fase 12
*/
public class Fase12Bean{

    private String country;
    private String password;

    private ArrayList<Album> albums = new ArrayList<Album>();

    public Fase12Bean(){

    }

    public String getCountry(){
        return country;
    }

    public String getPassword(){
        return password;
    }

    public ArrayList<Album> getAlbums() throws ParserConfigurationException, XPathExpressionException{
        DataModel datamodel = new DataModel();
        albums = datamodel.getQ1Albums(country); //Guardamos los álbumes encontrados según país
        return albums;
    }

    public void setCountry(String newCountry){
        country=newCountry;
    }

    public void setPassword(String newPassword){
        password=newPassword;
    }

    public void setAlbums(Album newAlbum){
        albums.add(newAlbum);
    }
}