package com.app.dmm;

import com.app.dmm.core.utils.RsaUtils;
import org.junit.jupiter.api.Test;

public class RsaTest {
    private String publicFile = "C:\\Users\\windy\\Desktop\\VBS\\rsa_key.pub";
    private String privateFile = "C:\\Users\\windy\\Desktop\\VBS\\rsa_key";

    /**生成公钥和私钥
     * @throws Exception
     * @version 1.0
     */
    @Test
    public void generateKey() throws Exception{

        RsaUtils.generateKey(publicFile,privateFile,"abcdmmlee",2048);

    }

}