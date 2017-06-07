package com.example.teja.threads;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by teja on 23-03-2017.
 */

public class ChelseaFeed {
    private String title;
    private String link;
    private String description;
    private Date date;
    private String imgurl;
    ChelseaFeed(){
        imgurl="a";
    }

    public String getLink() {
        return link;
    }

    public String getImgurl(){return imgurl;}
    public void setImgurl(String imgurl){this.imgurl=imgurl;}

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String s){
        try {
            this.date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        }
    public Date getDate(){
        return date;
    }
    @Override
    public String toString() {
        return "ChelseaFeed{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date.toString() + '\'' +
                "img  :"+imgurl+"?????????????"+
                '}';
    }
}
