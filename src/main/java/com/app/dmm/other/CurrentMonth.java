package com.app.dmm.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentMonth {
    /**
     * 获取当前月第一天
     * @return
     */
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
        System.out.println(df.format(new Date())+"-01");// new Date()为获取当前系统时间
    }

}
