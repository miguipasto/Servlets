package p3;

import p3.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

/*
    Bean para mostrar la Fase 11
*/
public class Fase11Bean{

    private String name;
    private String password;
    private ArrayList<String> countries = new ArrayList<String>();

    public Fase11Bean(){
    }

    public Fase11Bean(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public ArrayList<String> getCountries() throws ParserConfigurationException, XPathExpressionException{
        DataModel datamodel = new DataModel();
        countries = datamodel.getQ1Countries(); //Guardamos los paises encontrados
        return countries;
    }

    public void setName(String newName){
        name=newName;
    }

    public void setPassword(String newPassword){
        password=newPassword;
    }

    public void setCountries(String newCountry){
        countries.add(newCountry);
    }
}