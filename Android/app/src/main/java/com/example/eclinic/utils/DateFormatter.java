package com.example.eclinic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private Date date;
    public DateFormatter(String timestamp) throws ParseException {
        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(timestamp);
    }
    public String getDateFormat1(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, hh aa");
        return dateFormat.format(this.date);
    }
    public String getTimeFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
        return dateFormat.format(this.date);
    }

    public String getDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy EEE");
        return dateFormat.format(this.date);
    }
    public String getFullDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(" dd-MM-yyyy EEE hh:mm");
        return dateFormat.format(this.date);
    }
}
