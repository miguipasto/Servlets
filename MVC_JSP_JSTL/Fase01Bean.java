package p3;

import p3.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

/*
    Bean para mostrar la Fase 01
*/
public class Fase01Bean{

    private String password = new String();

    public Fase01Bean(){
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String newPassword){
        password=newPassword;
    }

}