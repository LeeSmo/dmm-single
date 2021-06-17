package com.app.dmm.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeStamp2DateTest {
    public static void main(String[] args) throws ParseException {
        String time = "1607925032284";
        String t = String.valueOf(Long.parseLong(time)/1000);
        String date = timeStamp2Date(t, "yyyy-MM-dd");
        System.out.println("date="+date);//运行输出:date=2016-08-04 10:34:42
        //创建SimpleDateFormat对象实例并定义好转换格式
        SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd");

        Date dates = null;
        try {
            // 注意格式需要与上面一致，不然会出现异常
            dates = sdfa.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.err.println("字符串转换成时间:" + date);
    }

    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }
}
