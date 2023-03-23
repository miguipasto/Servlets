package p4;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

import p4.*;

public  class Song implements Comparable<Song>{

    
    public Song(){ //Constructor vacío

    }   

    private String title;
    private String duration;
    private String genres[];
    private String genre;
    private String composer;
    private String muml;
    private String sid;
    private String lang;

    public Song(String title, String duration, String genres[], String composer, String muml, String sid, String lang){
        this.title=title;
        this.duration=duration;
        this.genres=genres;
        this.composer=composer;
        this.muml=muml;
        this.sid=sid;
        this.lang=lang;
    }

    /* Getters */
    public String getTitle(){
        return title;
    }

    public String getDuration(){
        return duration;
    }

    public String[] getGenres(){
        return genres;
    }

    public String getGenre(){
        genre = genres[0];
        for (int j=1; j<genres.length; j++){
            genre = genre+","+genres[j];
        }
        return genre;
    }

    public String getComposer(){
        return composer;
    }

    public String getMuML(){
        return muml;
    }

    public String getSid(){
        return sid;
    }

    public String getLang(){
        return lang;
    }

    /* SETTERS */
    public void setTitle(String newTitle){
        title=newTitle;
    }

    public void setDuration(String newDuration){
        duration=newDuration;
    }

    public void setGenres(String [] newGenres){
        genres=newGenres;
    }

    public void setComposer(String newComposer){
        composer=newComposer;
    }

    public void setMuml(String newMuml){
        muml=newMuml;
    }

    public void setSid(String newSid){
        sid=newSid;
    }

    public void setLang(String newLang){
        lang=newLang;
    }

    /* CompareTo de dos songs, se ordena:
        -Por el número de géneros a los que pertenece ( de menos a mayor )
        -Si tienen el mismo género lo hacemos por orden alfabético
    */
    public int compareTo(Song song){
        Song songComp = (Song) song;

        if(this.getGenres().length > songComp.getGenres().length){
            return 1; //Están bien ordenados
        } 

        if(this.getGenres().length == songComp.getGenres().length){ //Mismo número de géneros
            return title.compareTo(songComp.getTitle());    //Ordena por orden alfabético         
        } else {
            return -1; //Están mal ordenados, le damos la vuelta
        }
    }
}