package com.app.dmm.core.utils.License;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
public class LicenseUtils {

    /**
     * 项目集群及机器验证设计思路
     * 1、项目启动获取服务器信息，MD5加密作为SID信息
     * 2、
     * License：就是最终生成的激活码字符串；
     *
     * AesKey16：随机生成的16位AES密钥，选用固定长度密钥，因此不用拼接AesKey长度；
     *
     * AesEnc(data).length：加密后的授权信息长度，由于授权信息会变动，因此需要拼接该密文长度，方便截取；
     *
     * AesEnc(data)：AES加密后的授权信息；
     *
     * RsaSign(AesEnc(data))：RSA私钥签名后的加密授权信息。
     *
     * License = AesKey16 + AesEnc(data).length + AesEnc(data) + RsaSign(AesEnc(data));
     */

    /*公钥的key*/
    private static final String RSA_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBy4d0QegSXLCshXqa81buxUXAkZgGSqhiWm+2eC6ThggpfGUberNFgOGyanGWtujEy2ewnA/hM//FRPh4gAGqriQBPDSbiE6IICtxaFAsnarSs1U39ULQJi9c7uGuMxmjAXs8pTd4102Yt1NBoevviUhgU0z0kLAW5PYVh9m0uwIDAQAB";
    /*私钥的key*/
    private static final String RSA_PRI_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIHLh3RB6BJcsKyFeprzVu7FRcCRmAZKqGJab7Z4LpOGCCl8ZRt6s0WA4bJqcZa26MTLZ7CcD+Ez/8VE+HiAAaquJAE8NJuIToggK3FoUCydqtKzVTf1QtAmL1zu4a4zGaMBezylN3jXTZi3U0Gh6++JSGBTTPSQsBbk9hWH2bS7AgMBAAECgYBqunO9s1wjjThyBKhWtfYsDOdYe+AofEeN6JOn1ZdnE3Wut21kdTbo1hvA3CpfJjStbhzrasjcBCgzjI1MeOXkyOdTI70TT9Bmy6UjKdLpdbZ/e3bGLmFd9JRYxTMJ1u0k/GDVLMR20CHjyBvPygEne9U2dSv9P1M5d1AT6sZTwQJBAMVTYGYrM037ZfAoiWO5rtYbw2TFVK6ea9ajqXoeDoFmk3vASWNwUyVRxGSiW5uW4Q5Zid7kcuDaJXONC1QmnTECQQCoY6dKpiMuWHXrDfr++2TCx7Uk5lGOYCe+P30HKdaeXr27YeHL2vLwX1KR8xKEnOUSJRroCm/UG4++TYAmmMWrAkA1oJI7UhRQZjlvCmVEYAtfDw6MTYDVMCLYo4QEtzR0AH+mizymtLk0FEPOob1JzLh/YOAyXl3GNCMngzaH+TKRAkEAkjFCkpq+vIPsjFAs86bKihjcedbEQ7nTh2aTY969B+31B3QpltTWSj5X74J7N0sHzcMNIxu8jwgGug+LDPdh1QJAepy1sxwp5aCSrzOp0qInfYHNxf9aN5ZxydmBHpRauc73dm/MXtNHzy8nSBXtx7agNX5vumCAh7jvQxv+7kc4lQ==";

    /**
     * 初始化授权
     */
    public static String initLicense(LicenseCheckModel model) {
        String lic = "";
        try {
            //获取随机字符串 方法 1  RandomStringUtils  方法 2  MyRandomStringUtils
            //String aesKey = RandomStringUtils.random(10);
            String aesKey = MyRandomStringUtils.getRandomString(16);
            //System.err.println("aesKey............");
            //System.err.println(aesKey);
            String json = JSONObject.toJSONString(model);
            //System.err.println("json............");
            //System.out.println(json);
            String encData = AESUtils.encrypt(json, aesKey);
            //System.err.println("encData............");
            //System.out.println(encData);
            String encDataLength = Integer.toHexString(encData.length());
            //System.out.println(encDataLength);
            //私钥加密
            byte[] data = encData.getBytes();
            //byte[] encodedData = RSAUtils.encryptByPrivateKey(data, RSA_PRI_KEY);
            String encodedData = RSAUtils.sign(data, RSA_PRI_KEY);

            //System.out.println("加密后：\r\n" + encodedData);
            lic = aesKey + encDataLength + encData + encodedData;
            return lic;
        } catch (Exception e) {
            e.printStackTrace();
            return lic;
        }
    }
    /**
     * 解密激活
     * 激活步骤：
     *
     *     从程序中读取License激活码；
     *     根据生成规则分割、截取出各段信息；
     *     使用RSA公钥验签算法对AesEnc(data)、RsaSign(AesEnc(data))进行验证，通过则说明信息可信；
     *     接着使用激活码中截取的AesKey16对AesEnc(data)解密，得到可信的明文授权信息；
     *     最后使用可信的明文授权信息与当前环境进行授权比对，
     *     如AppId、授权时间等，即可完成激活流程。
     */
    public static boolean checkLic(String lic) {
        if (StringUtils.isEmpty(lic)) {
            return false;
        }
        try {
            String aesKey = lic.substring(0, 16);
            int encDataLength = Integer.parseInt(lic.substring(16, 19), 16);
            String encData = lic.substring(19, 19 + encDataLength);
            String sign = lic.substring(19 + encDataLength);
            if (!RSAUtils.verify(encData.getBytes(), RSA_PUB_KEY, sign)) {
                log.info("证书签名错误，请联系管理员......");
                return false;
            }
            String data = AESUtils.decrypt(encData,aesKey);
            LicenseCheckModel checkModel = JSON.parseObject(data, LicenseCheckModel.class);
            Map<String,Object> serverInfoMap = ServerInfoUtils.getServerInfos();
            if(!checkModel.getMacAddress().equals(serverInfoMap.get("MAC"))) {
                log.info("证书安装失效，请联系管理员......");
                return false;
            }
            if(!checkModel.getCpuSerial().equals(serverInfoMap.get("CPU"))) {
                log.info("证书硬件信息校验失败，请联系管理员......");
                return false;
            }
            if(!checkModel.getMainBoardSerial().equals(serverInfoMap.get("BOS"))) {
                log.info("证书硬件授权失败，请联系管理员......");
                return false;
            }

            if(!checkModel.getCertType().equals("E")) {
                Date notBefore = checkModel.getMakeDate();
                Date notAfter = checkModel.getDueDate();
                //Date类的一个方法，如果a早于b返回true，否则返回false
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(!(notBefore.before(now) && now.before(notAfter))) {
                    log.info("证书有效期失效......截止时间  "+sdf.format(notAfter));
                    return false;
                }else {
                    log.info("证书在有效期内......截止时间  "+sdf.format(notAfter));
                }
            }
            //System.err.println(checkModel.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("证书未知异常，请联系管理员......");
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        String dd = "NTHPwYSjr05CKi90140uEc/SoumNZruP0La8cdU+7UGQhD3VZMboeWWhqO6xREST05uMh88gQxsNI1EoYonAsLbFuBHy0FV3tH49n0aRFhJZCnlZ5B4asEO2jYf42RxK10xLm20jONDA9MkzpInQTrVdl/vxJVZiSKDH6QVbNCcJ6NFYIdJCfRhzC2P1R/i87IQiO1UGo7B9dbSrwe24Epf2oZ/egvYfQHZogz72obYfEM5CL+Ryt6C4B8r1Ru+6YEXHT/P4YsNMgchi9BEy9gK1sT1KvpR24K5fA/DQ3PQvwx8zZY48J7DOhFzjsRy59rn5DSbhcifl870dKmANtp1Hd5tcIK2fVwXd5Pkb0JjlTWV507gPwtTsaCPz2lMe9n/RUGacy2PFOjLlJ93NamPjm5dYuVZ78q12qrU6nLzOAEOipSYjmi2AGUZehnEuZfB+3pYqq8vLElSO8CeHVvkK05d3buz9pPBmlNRBGVF2QD8hbQieIr2R4XnKLg=";
        String bb = dd.substring(16, 19);
        int encDataLength = Integer.parseInt(dd.substring(16, 19), 16);

        checkLic(dd);


    }


}
