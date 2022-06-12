package com.example.codeexpdeez;

public class ScheduleEvent {

    private String title;
    private String details;
    private String date;
    private String unit;
    private String coy;

    ScheduleEvent(){

    }

    public String getDate() { return date; }

    public String getDetails() { return details; }

    public String getTitle() { return title; }

    public String getUnit() { return unit; }

    public String getCoy() { return coy; }


    public void setDate(String date) { this.date = date; }

    public void setDetails(String details) { this.details = details; }

    public void setTitle(String title) { this.title = title; }

    public void setUnit(String unit) { this.unit = unit; }

    public void setCoy(String coy) { this.coy = coy; }

}