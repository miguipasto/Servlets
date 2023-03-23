package p3;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

import p3.*;

public class Album implements Comparable<Album>{

        private String name;
        private String country;
        private String interprete;
        private String ISBN;
        private String company;
        private String aid;
        private String format;
        private int year;
        private String review;

        public Album(){ //Constructor vacío

        }
     
        //Constructor
        public Album(String name, String country, String interprete, String ISBN, String company, String aid, String format, int year, String review){
            this.name = name;
            this.country = country;
            this.interprete = interprete;
            this.company = company;
            this.aid = aid;
            this.format = format;
            this.year = year;
            this.review = review;
        }

        /* Getters */
        public String getName(){
            return name;
        }

        public String getCountry(){
            return country;
        }

        public String getInterprete(){
            return interprete;
        }

        public String getISBN(){
            return ISBN;
        }

        public String getCompany(){
            return company;
        }

        public String getAid(){
            return aid;
        }

        public String getFormat(){
            return format;
        }

        public int getYear(){
            return year;
        }

        public String getReview(){
            return review;
        }

        
        /* SETTERS */

        public void setName(String newName){
            name=newName;
        }

        public void setCountry(String newCountry){
            country=newCountry;
        }

        public void setInterprete(String newInterprete){
            interprete=newInterprete;
        }

        public void setISBN(String newISBN){
            ISBN=newISBN;
        }

        public void setCompany(String newCompany){
            company=newCompany;
        }

        public void setAid(String newAid){
            aid=newAid;
        }

        public void setFormat(String newFormat){
            format=newFormat;
        }

        public void setYear(int newYear){
            year=newYear;
        }

        public void setReview(String newReview){
            review=newReview;
        }


        /* CompareTo de dos albumes, se ordena:
            -Por año
            -Si son del mismo año por orden alfabético
        */
        public int compareTo(Album album){
            Album albumComp = (Album) album;

            if(this.getYear() > albumComp.getYear()){ //Están bien ordenados
                return 1;
            } 
            if(this.getYear() == albumComp.getYear()){ //Mismo año
                return name.compareTo(albumComp.getName());    //Ordena por orden alfabético         
            } else { //Están en orden inverso, le damos la vuelta
                return -1; 
            }
        }
    }