package com.app.dmm.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeDemo {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
       rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_YEAR,0);//日期加10天

        Date dt1=rightNow.getTime();
       String reStr = sdf.format(dt1);
        System.out.println("之前："+reStr);

        String dateNow = sdf.format(new Date());
        System.out.println("当前时间为："+dateNow);

       /* Date startTime= sdf.parse("2020-10-11");
        Date endTime= sdf.parse(dateNow);
        if(startTime.getTime() < endTime.getTime()) {
            System.out.println("ddddddddd");
        }*/
    }
}
