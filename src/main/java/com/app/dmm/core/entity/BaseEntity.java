package com.app.dmm.core.entity;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class BaseEntity {

    /**
     * 是否用户已被冻结 1正常(解冻) 2冻结
     */
    public static final Integer USER_UNFREEZE = 1;
    public static final Integer USER_FREEZE = 2;
    /** 未删除、否、禁用、等标志 */
    public static final String DELETE_FLAG_N = "n";
    /** 已经删除、是、启用、等标志 */
    public static final String DELETE_FLAG_Y = "y";
    @Id
    //@Column(name = "id",unique = true, nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @KeySql(genId = UUIdGenId.class)

    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
