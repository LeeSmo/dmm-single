package com.app.dmm.other;

import org.apache.commons.lang3.StringUtils;

public class StringBTest {

    public static void main(String[] args) {
        String ciIds = "122222222,111112121";
        String[] cis = ciIds.split(","); //获取资产ID
        String[] ci = new String[cis.length];
        for (int i = 0; i < cis.length; i++) {
            ci[i] = "?";
        }
        StringUtils.join(ci, ",");
        System.out.println(ciIds);
        System.out.println(cis.toString());
        System.out.println(ci);
        System.out.println(StringUtils.join(ci, ","));
    }
}
