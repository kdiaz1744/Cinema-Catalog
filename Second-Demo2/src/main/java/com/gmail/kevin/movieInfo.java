package com.gmail.kevin;

import java.io.File;
import java.lang.Double;
import com.gmail.kevin.movieDataProvider;

public class movieInfo implements Cloneable {

    private Integer id;
    private String name = "";
    private String synopsis = "";
    private Integer rating = 1;
    private Integer year = 1990;
    private long amount = 1;
    private Boolean screening = false;

    public movieInfo(){
    }

    public movieInfo(Integer id, String name, String synopsis, Integer rating, Integer year, long amount, boolean screan){
    //public movieInfo(Integer id, String name, String synopsis, Integer rating, Integer year){
        this.id = id;
        this.name = name;
        this.synopsis = synopsis;
        this.rating = rating;
        this.year = year;
        this.amount = amount;
        this.screening = screan;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Boolean getScreening() {
        return screening;
    }

    public void setScreening(Boolean screening) {
        this.screening = screening;
    }

    @Override
    public movieInfo clone() {
        try {
            return (movieInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
