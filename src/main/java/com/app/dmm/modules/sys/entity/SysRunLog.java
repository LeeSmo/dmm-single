package com.app.dmm.modules.sys.entity;

import com.app.dmm.core.entity.BaseEntity;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "SYS_ROLE")
public class SysRunLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4640103339549190631L;
    private String userId;          // 用户id
    private String username;        // 用户名
    private String operation;       // 用户操作
    private int time;               // 响应时间
    private String method;          // 请求方法
    private String params;          // 请求参数
    private Date createTime;        // 创建时间
    private String type;            //类型

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
