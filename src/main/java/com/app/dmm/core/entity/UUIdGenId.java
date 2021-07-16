package com.app.dmm.core.entity;

import tk.mybatis.mapper.genid.GenId;

import java.util.UUID;

/**
 * @author lee
 */
public class UUIdGenId implements GenId<Long> {
    @Override
    public Long genId(String s, String s1) {
        //return UUID.randomUUID().toString().replace("-","");
        //return UUID.randomUUID().getLeastSignificantBits();
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}