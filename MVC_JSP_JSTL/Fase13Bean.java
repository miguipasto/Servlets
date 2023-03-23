package p3;

import p3.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

/*
    Bean para mostrar la Fase 13
*/
public class Fase13Bean{

    private String country;
    private String password;
    private String aid;

    private ArrayList<Song> songs = new ArrayList<Song>();

    public Fase13Bean(){

    }

    public String getPassword(){
        return password;
    }
    
    public String getCountry(){
        return country;
    }

    public String getAid(){
        return aid;
    }

    public ArrayList<Song> getSongs() throws ParserConfigurationException, XPathExpressionException{
        DataModel datamodel = new DataModel();
        songs = datamodel.getQ1Songs(country,aid); //Guardamos las canciones encontradas según país y aid
        return songs;
    }

    public void setPassword(String newPassword){
        password=newPassword;
    }

    public void setCountry(String newCountry){
        country=newCountry;
    }
    
    public void setAid(String newAid){
        aid=newAid;
    }
}