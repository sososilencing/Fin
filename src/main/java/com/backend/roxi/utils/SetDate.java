package com.backend.roxi.utils;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Roxi酱
 */
public class SetDate {
    public static Date setDates(int month,int day,int hour,int min,int sec){
        Calendar calendar=new GregorianCalendar();
        Date date=new Date();
        calendar.setTime(date);
        boolean flag=false;
        if(month>12 || month<0){
            flag=true;
        }
        else if(day>30 || day<0){
            flag=true;
        }
        else if(hour>24 || hour<0){
            flag=true;
        }
        else if(min>60 || min<0){
            flag=true;
        }
        else if(sec>60 || sec<0){
            flag=true;
        }
        try{
            if(flag){

            }else {
                calendar.add(Calendar.MONTH,month);
                calendar.add(Calendar.DAY_OF_WEEK,day);
                calendar.add(Calendar.HOUR,hour);
                calendar.add(Calendar.MINUTE,min);
                calendar.add(Calendar.SECOND,sec);

            }
        }catch (Exception e){

        }
        return calendar.getTime();
    }
}
