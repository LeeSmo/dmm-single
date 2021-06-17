package com.app.dmm.modules.user.entity;

import com.app.dmm.core.entity.BaseEntity;
import com.app.dmm.modules.sys.entity.SysRole;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "sys_user")
public class SysUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2601234073734313278L;

    /**
     * 用户名
     */
    //@Column(name = "user_name")
    private String userName;

    /**
     * 真实姓名
     */
    //@Column(name = "real_name")
    private String realName;

    /**
     * 密码
     */
   // @Column(name = "password")
    private String password;

    /**
     * 密码盐
     */
    //@Column(name = "salt")
    private String salt;
    /**
     * 手机号
     */
    //@Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 邮箱
     */
   // @Column(name = "email")
    private String email;

    /**
     * 微信号
     */
    @Column(name = "wechat_id")
    private String weChatId;
    /**
     * QQ
     */
    @Column(name = "qq")
    private String qNumber;
    /**
     * 身份证号码
     */
    //@Column(name = "card_id")
    private String cardId;

    /**
     * 角色ID列表
     */
    //@Column(name = "role_ids")
    private String roleIds;

    /**
     * 部门ID
     */
    //@Column(name = "depart_id")
    private String departId;

    /**
     * 状态
     */
   // @Column(name = "state")
    private Integer state;
    /**
     * 0正常,1隐藏
     */
    //@Column(name = "data_flag")
    private Integer dataFlag;

    private String deleteFlag;

    private List<String> sysRoleList;

    public SysUser(String userName) {
        super();
        this.userName = userName;
        setCreateTime(new Date());
    }

    public SysUser() {

    }

    public SysUser(int i, String java的架构师技术栈, String man) {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public String getqNumber() {
        return qNumber;
    }

    public void setqNumber(String qNumber) {
        this.qNumber = qNumber;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(Integer dataFlag) {
        this.dataFlag = dataFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public List<String> getSysRoleList() {
        return sysRoleList;
    }

    public void setSysRoleList(List<String> sysRoleList) {
        this.sysRoleList = sysRoleList;
    }
}