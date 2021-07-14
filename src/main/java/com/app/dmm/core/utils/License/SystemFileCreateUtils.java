package com.app.dmm.core.utils.License;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SystemFileCreateUtils {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("1哈哈 黑");
        stringList.add("2嘿嘿 黑");
        writeDataHub(stringList, "111");
        System.out.println(111);
    }

    /**
     * 写入txt文件
     *
     * @param result
     * @param fileName
     * @return
     */
    public static boolean writeDataHub(List<String> result, String fileName) {
        long start = System.currentTimeMillis();
        String filePath = "C:\\Users\\windy\\Desktop\\txt";
        StringBuilder content = new StringBuilder();
        boolean flag = false;
        BufferedWriter out = null;
        try {
            if (result != null && !result.isEmpty() && StringUtils.isNotEmpty(fileName)) {
                fileName += "_" + System.currentTimeMillis() + ".info";
                File pathFile = new File(filePath);
                if (!pathFile.exists()) {
                    pathFile.mkdirs();
                }
                String relFilePath = filePath + File.separator + fileName;
                File file = new File(relFilePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
//                //标题头
//                out.write("curr_time,link_id,travel_time,speed,reliabilitycode,link_len,adcode,time_stamp,state,public_rec_time,ds");
//                out.newLine();
                for (String info : result) {

                    out.write(info);
                    out.newLine();
                }
                flag = true;
//                logger.info("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
                System.out.println("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return flag;
        }
    }

    public static boolean writeInitDataHub(String data, String filePath, String fileName) {
        long start = System.currentTimeMillis();

        StringBuilder content = new StringBuilder();
        boolean flag = false;
        BufferedWriter out = null;
        try {
            if (data != null && !data.isEmpty() && StringUtils.isNotEmpty(fileName)) {
                //fileName += "_" + System.currentTimeMillis() + ".info";
                File pathFile = new File(filePath);
                if (!pathFile.exists()) {
                    pathFile.mkdirs();
                }
                //String relFilePath = filePath + File.separator + fileName;
                String relFilePath = filePath + fileName+ ".info";
                File file = new File(relFilePath);
                if (!file.exists()) {
                    file.createNewFile();
                }
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
//                //标题头
//                out.write("curr_time,link_id,travel_time,speed,reliabilitycode,link_len,adcode,time_stamp,state,public_rec_time,ds");
//                out.newLine();

                out.write(data);
                out.newLine();
                flag = true;
                log.info("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
                //System.out.println("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return flag;
        }
    }
}
