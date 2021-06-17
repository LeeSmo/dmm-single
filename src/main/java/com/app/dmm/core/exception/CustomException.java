package com.app.dmm.core.exception;

import com.app.dmm.core.enums.ResultCode;

/**
 *自定义异常
 */

public class CustomException extends RuntimeException {

    private final Integer code;   //异常状态码


    private final String method;  //发生的方法，位置等


    public CustomException(ResultCode resultEnum, String method) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.method = method;
    }


    public CustomException(Integer code, String message, String method) {
        super(message);
        this.code = code;
        this.method = method;
    }

    public Integer getCode() {
        return code;
    }

    public String getMethod() {
        return method;
    }
}
