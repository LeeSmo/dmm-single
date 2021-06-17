package com.app.dmm.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTestUtil {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        //String str="20110823";
        //Date dt=sdf.parse(str);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        //rightNow.add(Calendar.YEAR,-1);//日期减1年
       // rightNow.add(Calendar.MONTH,3);//日期加3个月
        rightNow.add(Calendar.DAY_OF_YEAR,-30);//日期加10天
        Date dt1=rightNow.getTime();
        String reStr = sdf.format(dt1);
        System.out.println(reStr);


        //创建SimpleDateFormat对象实例并定义好转换格式
        SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.err.println("把当前时间转换成字符串：" + sdfa.format(new Date()));

        Date date = null;
        try {
            // 注意格式需要与上面一致，不然会出现异常
            date = sdfa.parse("2005-12-15 15:30:23");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.err.println("字符串转换成时间:" + date);
    }
}