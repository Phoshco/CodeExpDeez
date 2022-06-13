package com.example.codeexpdeez;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DaysCounter {

    Date day;
    Date today;
    SimpleDateFormat dtf = new SimpleDateFormat("dd MM yyyy");
    String difference;

    DaysCounter(String day){
        String[] adw= day.split("-");
        String daytemp = adw[2]+" "+adw[1]+" "+adw[0];
        String todaytemp = today();

        try{
            this.day = dtf.parse(daytemp);
            this.today = dtf.parse(todaytemp);
        } catch (ParseException e){
            e.printStackTrace();
        }
        long diff = this.day.getTime()-this.today.getTime();
        this.difference = ""+TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

    }

    public String getDifference(){return this.difference;}

    private String today(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = 1+Calendar.getInstance().get(Calendar.MONTH);
        int dayday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String mth = ""+month;
        if (mth.length() == 1){
            mth = "0"+mth;
        }
        String dayt = ""+dayday;
        if (dayt.length() == 1){
            dayt = "0"+dayt;
        }
        String result = dayt+" "+mth+" "+year;
        return result;
    }



}
