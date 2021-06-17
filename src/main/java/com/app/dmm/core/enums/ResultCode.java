package com.app.dmm.core.enums;

import io.swagger.models.auth.In;
import org.omg.PortableInterceptor.SUCCESSFUL;

/**
 * 状态码枚举
 */

public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(200, "操作成功"),
    FAILURE(400, "业务异常"),
    SERVER_ERROR(500, "服务内部异常"),
    NO_AUTHZ(510, "访问权限认证未通过"),
    REQ_METHOD_NOT_SUPPORT(110,"请求方式不支持"),
    /* 参数错误： 1001-1999 */
    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_BLANK(1002,"参数为空"),
    PARAM_TYPE_BIND_ERROR(1003,"参数类型错误"),
    PARAM_NOT_COMPLETE(1004,"参数缺失"),
    PARAM_FORMAT_ERROR(1005,"参数格式错误"),
    /* 用户信息错误： 2001-2999*/
    USER_NOT_LOGGED_IN(2001,"用户未登录，访问的路径需要验证，请登录"),
    USER_LOGIN_ERROR(2002,"账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003,"账号已被禁用"),
    USER_NOT_EXIST(2004,"用户不存在"),
    USER_HAS_EXISTED(2005,"用户已存在"),
    /* 统一异常响应码 */
    NULL_POINT_EXCEPTION(10003,"空指针异常"),
    CUSTOM_EXCEPTION(10004,"自定义异常"),
    UNKNOWN_EXCEPTION(10005,"未知异常"),
    ;

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static ResultCode getByCode(int code){
        for (ResultCode resultEnum : ResultCode.values()) {
            if(code == resultEnum.getCode()){
                return resultEnum;
            }
        }
        return null;
    }

}
