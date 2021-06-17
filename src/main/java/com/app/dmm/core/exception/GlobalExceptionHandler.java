package com.app.dmm.core.exception;

import com.app.dmm.core.ApiResult;
import com.app.dmm.core.enums.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;


/**
 * 异常处理的大致流程主要如下。
 * 异常信息抛出 -> ControllerAdvice 进行捕获格式化输出内容
 * 手动抛出CustomException并传入ReulstEnum ——> 进行捕获错误信息输出错误信息。
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义异常
     */
    @ExceptionHandler(value = CustomException.class)
    public ApiResult processException(CustomException e) {
        logger.error("位置:{} -> 错误信息:{}", e.getMethod() ,e.getLocalizedMessage());
        return ApiResult.error(ResultCode.getByCode(e.getCode()).getMessage());
    }

    /**
     * 拦截表单参数校验
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class})
    public ApiResult bindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ApiResult.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    /**
     * 拦截JSON参数校验
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult bindException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ApiResult.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    /**
     * 参数类型错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResult methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.error("错误信息{}", e.getLocalizedMessage());
        return ApiResult.error(ResultCode.PARAM_TYPE_BIND_ERROR.getMessage());
    }

    /**
     * 参数格式错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResult httpMessageNotReadable(HttpMessageNotReadableException e) {
        logger.error("错误信息:{}", e.getLocalizedMessage());
        return ApiResult.error(ResultCode.PARAM_FORMAT_ERROR.getMessage());
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult httpReqMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        logger.error("错误信息:{}", e.getLocalizedMessage());
        return ApiResult.error(ResultCode.REQ_METHOD_NOT_SUPPORT.getMessage());
    }

    /**
     * 通用异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public ApiResult exception(Exception e) {
        e.printStackTrace();
        return ApiResult.error(ResultCode.UNKNOWN_EXCEPTION.getMessage());
    }
}
