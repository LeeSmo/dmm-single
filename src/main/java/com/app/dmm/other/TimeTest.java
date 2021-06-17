package com.app.dmm.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {
    public static void main(String[] args) throws ParseException {
       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = sdf.format(new Date());
        System.out.println("当前时间为："+dateNow);
        Date date = sdf.parse(dateNow); //字符转日期
        System.err.println("当前时间日期格式："+date);
        //具体的年、月、日可利用substring截取
        //dateNow.substring(开始下标，结束下标),下标原则：从0开始，包前不包后
        System.out.println(dateNow.substring(0,4)+"年"+dateNow.substring(5,7)+"月"+dateNow.substring(8,10)+"日");

    }
}
