package com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieEntityShort {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("release_date")
    @Expose
    private String year;
    @SerializedName("id")
    @Expose
    private  String id;
    @SerializedName("poster_path")
    @Expose
    private  String poster;

//    public Movie(String title, String year, String id){
//        this.title = title;
//        this.year = year;
//        this.id = id;
//    }

    public String getTitle() {
        return title;
    }

    public String getYear() {

//        if(year.equals("")){
//            year = "1789-07-14";
//        }
//
//        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = null;
//        try {
//            date = dt.parse(year);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
//
//        return df.format(date);
        return year;
    }

    public String getId() {
        return id;
    }

    public String getPoster() {
        return poster;
    }
}
