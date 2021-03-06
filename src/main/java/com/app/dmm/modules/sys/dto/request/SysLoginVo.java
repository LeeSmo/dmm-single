package com.app.dmm.modules.sys.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 登录接口对象
 */
@ApiModel(value="登录请求类", description="登录请求类")
public class SysLoginVo implements Serializable {

    @ApiModelProperty(value = "用户名", required=true)
    private String username;

    @ApiModelProperty(value = "密码", required=true)
    private String password;

    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "验证码key")
    private String checkKey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getCheckKey() {
        return checkKey;
    }

    public void setCheckKey(String checkKey) {
        this.checkKey = checkKey;
    }
}
