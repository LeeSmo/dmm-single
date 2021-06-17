package com.app.dmm.core.controller;

import com.app.dmm.core.ApiResult;
import com.app.dmm.core.enums.ResultCode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * 基础控制器
 */

public class BaseController {

    private  static Logger logger = LogManager.getLogger(BaseController.class);

    /**
     * 成功默认消息
     */
    private static final Integer CODE_SUCCESS = 0;
    private static final String MSG_SUCCESS = "操作成功！";

    /**
     * 失败默认消息
     */
    private static final Integer CODE_FAILURE = 1;
    private static final String MSG_FAILURE = "请求失败！";
    /**
     * 完成消息构造
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    protected <T> ApiResult<T> message(Integer code, String message, T data){
        ApiResult<T> response = new ApiResult<>();
        response.setCode(code);
        response.setMessage(message);
        if(data!=null) {
            response.setResult(data);
        }
        return response;
    }

    /**
     * 请求成功空数据
     * @param <T>
     * @return
     */
    protected <T> ApiResult<T> success(){
        return message(0, "请求成功！", null);
    }



    /**
     * 请求成功，通用代码
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    protected <T> ApiResult<T> success(String message, T data){
        return message(CODE_SUCCESS, message, data);
    }


    /**
     * 请求成功，仅内容
     * @param data
     * @param <T>
     * @return
     */
    protected <T> ApiResult<T> success(T data){
        return message(CODE_SUCCESS, MSG_SUCCESS, data);
    }


    /**
     * 请求失败，完整构造
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    protected <T> ApiResult<T> failure(Integer code, String message, T data){
        return message(code, message, data);
    }

    /**
     * 请求失败，消息和内容
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    protected <T> ApiResult<T> failure(String message, T data){
        return message(CODE_FAILURE, message, data);
    }

    /**
     * 请求失败，消息
     * @param message
     * @return
     */
    protected <T> ApiResult<T> failure(String message){
        return message(CODE_FAILURE, message, null);
    }

    /**
     * 请求失败，仅内容
     * @param data
     * @param <T>
     * @return
     */
    protected <T> ApiResult<T> failure(T data){
        return message(CODE_FAILURE, MSG_FAILURE, data);
    }


    /**
     * 请求失败，仅内容
     * @param <T>
     * @return
     */
    protected <T> ApiResult<T> failure(){
        return message(CODE_FAILURE, MSG_FAILURE, null);
    }



    /**
     * 请求失败，仅内容
     * @param <T>
     * @return
     */
    protected <T> ApiResult<T> failure(ResultCode resultCode, T data){
        return message(resultCode.getCode(), resultCode.getMessage(), data);
    }



    /**
     * 请求失败，仅内容
     * @param ex
     * @param <T>
     * @return
     */
    /*protected <T> ResultResponse<T> failure(ServiceException ex){
        ResultResponse<T> apiRest = message(ex.getCode(), ex.getMsg(), null);
        return apiRest;
    }*/
}
