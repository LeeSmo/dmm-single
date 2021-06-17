package com.app.dmm.modules.sys.entity;

import com.app.dmm.core.entity.BaseEntity;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "SYS_ROLE")
public class SysRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleName; // 名称

    private String roleCode; // 编码

    private String deleteFlag; // 默认 n:未删除

    private String remark; // 备注

    private String showState; // 隐藏状态 y:显示，n:隐藏

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShowState() {
        return showState;
    }

    public void setShowState(String showState) {
        this.showState = showState;
    }
}
