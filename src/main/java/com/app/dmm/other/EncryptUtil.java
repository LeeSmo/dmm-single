package com.app.dmm.other;


import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import Decoder.BASE64Decoder;

public class EncryptUtil {

    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    public static final String KEY = "ASDQWRZXC1234321";

    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    public static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] base64Decode(String base64Code) throws Exception{
        return new BASE64Decoder().decodeBuffer(base64Code);
    }

    /*加密*/
    public static String aesEncrypt(String content) throws Exception {
        return base64Encode(aesEncryptToBytes(content));
    }
    public static byte[] aesEncryptToBytes(String content) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /*解密*/
    public static String aesDecrypt(String encryptStry) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStry));
    }
    public static String aesDecryptByBytes(byte[] encryptBytes) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }
    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }




    public static void main(String[] args) throws Exception {
        //{"urlType":"","loginIndex":"Y","key1":"admin","key2":"QdmwKERqQTuLsu3BGrVB4UxF3zrojJTBxTBT6CSBE/0=","j_kaptcha":"5369"}
        String content = "{\"urlType\":\"\",\"loginIndex\":\"Y\",\"key1\":\"admin\",\"key2\":\"QdmwKERqQTuLsu3BGrVB4UxF3zrojJTBxTBT6CSBE/0=\",\"j_kaptcha\":\"5369\"}";
        String password = "ASDQWRZXC1234321";
        //加密
        System.out.println("加密前：" + EncryptUtil.aesDecrypt(content) );
        //解密
        System.out.println("解密后：" +  EncryptUtil.aesDecrypt(password));

    }

}
