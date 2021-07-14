package com.app.dmm.core.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.*;

public class SignUtils {

    private static final String ALGORITHM_MD5 = "MD5";

    /**
     * sign 签名 （参数名按ASCII码从小到大排序（字典序）+key+MD5+转大写签名）升序
     * @param map
     * @return
     */
    public static String encodeSign(String characterEncoding,SortedMap<Object,Object> map, String key){
        if(StringUtils.isEmpty(key)){
            throw new RuntimeException("签名key不能为空");
        }
        Set<Map.Entry<Object,Object>> entries = map.entrySet();
        Iterator<Map.Entry<Object,Object>> iterator = entries.iterator();
        List<String> values = Lists.newArrayList();

        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = String.valueOf(entry.getKey());
            String v = String.valueOf(entry.getValue());
            if (StringUtils.isNotEmpty(v) && entry.getValue() !=null && !"sign".equals(k) && !"key".equals(k)) {
                values.add(k + "=" + v);
            }
        }
        values.add("key="+ key);
        String sign = StringUtils.join(values, "&");
        System.err.println("sign >>>>>>"+sign);
        //return encodeByMD5(sign,characterEncoding).toUpperCase();
        return MD5Utils.MD5Encode(sign,"utf-8").toUpperCase();
    }
    /**
     * 通过MD5加密
     *
     * @param algorithmStr    加密串
     * @param characterEncoding  编码格式
     * @return String
     */
    public static String encodeByMD5(String algorithmStr,String characterEncoding) {
        if (algorithmStr==null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_MD5);

            if(StringUtils.isNotEmpty(characterEncoding)) {
                messageDigest.update(algorithmStr.getBytes(characterEncoding));
            }else {
                messageDigest.update(algorithmStr.getBytes("utf-8"));
            }
            return new String(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        //String mfrchant_id="fffff";
        String merchant_id="190010002";
        String business_type="1005";
        String out_trade_no="1400000001";

        //String key="3A4BC4A4000CF1B5FFA9E351E6C1539E";
        String key="e8a314d5cfe87d4a8c26c83ad6398eb7";
        //String key="E8A314D5CFE87D4A8C26C83AD6398EB7";
        //parameters.put("mfrchant_id", mfrchant_id);
        parameters.put("merchant_id", merchant_id);
        parameters.put("business_type", business_type);
        parameters.put("out_trade_no",out_trade_no);
        String characterEncoding = "UTF-8";         //指定字符集UTF-8
        String mySign = encodeSign(characterEncoding,parameters,key);
        System.out.println("我 的签名是："+mySign);



    }

}
