package com.example.codeexpdeez;

import java.time.LocalDateTime;

public class AnnouncementObject {
    private String title;
    private String message;
    private String date;
    private String time;
    private String unit;
    private String coy;

    AnnouncementObject() {
    }

    public String getTitle() {
        return title;
    }
    public String getMessage(){
        return message;
    }
    public String getDate() {
        return date;
    }
    public String getTime(){return time;}
    public String getUnit(){
        return unit;
    }
    public String getCoy(){
        return coy;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setMessage(String message){
        this.message= message;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setTime(String time){this.time = time;}
    public void setUnit(String unit){
        this.unit= unit;
    }
    public void setCoy(String coy){
        this.coy= coy;
    }
}
