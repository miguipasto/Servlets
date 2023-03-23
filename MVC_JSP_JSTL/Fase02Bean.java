package p3;

import p3.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

/*
    Bean para mostrar la Fase 02
*/
public class Fase02Bean{

    private String password = new String();
    private ArrayList<String> fatalError = new ArrayList<String>();
    private ArrayList<String> error = new ArrayList<String>();

    public Fase02Bean(){
    }

    public String getPassword(){
        return password;
    }

    public ArrayList<String> getFatalError() throws ParserConfigurationException, XPathExpressionException{
        DataModel datamodel = new DataModel();
        fatalError = datamodel.getFatalError(); //Guardamos los ficheros fatalError
        return fatalError;
    }

    public ArrayList<String> getError() throws ParserConfigurationException, XPathExpressionException{
        DataModel datamodel = new DataModel();
        error = datamodel.getError(); //Guardamos los ficheros fatalError
        return error;
    }

    public void setPassword(String newPassword){
        password=newPassword;
    }

    public void setFatalError(String newFatalError){
        fatalError.add(newFatalError);
    }

    public void setError(String newError){
        error.add(newError);
    }
}