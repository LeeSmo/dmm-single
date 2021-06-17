package com.app.dmm.core;

import com.app.dmm.core.enums.ResultCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 */
@ApiModel(value="接口返回对象", description="接口返回对象")
public class ApiResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	@ApiModelProperty(value = "成功标志")
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	@ApiModelProperty(value = "返回处理消息")
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	@ApiModelProperty(value = "返回代码")
	private Integer code = 0;

	/**
	 * 返回数据对象 data
	 */
	@ApiModelProperty(value = "返回数据对象")
	private T result;

	/**
	 * 时间戳
	 */
	@ApiModelProperty(value = "时间戳")
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private long timestamp = System.currentTimeMillis();

	@ApiModelProperty(value = "token数据")
	private String token;

	public ApiResult() {
		
	}
	
	public ApiResult<T> success(String message) {
		this.message = message;
		this.code = ResultCode.SUCCESS.getCode();
		this.success = true;
		return this;
	}

	@Deprecated
	public static ApiResult<Object> ok() {
		ApiResult<Object> r = new ApiResult<Object>();
		r.setSuccess(true);
		r.setCode(ResultCode.SUCCESS.getCode());
		r.setMessage("成功");
		return r;
	}

	@Deprecated
	public static ApiResult<Object> ok(String msg) {
		ApiResult<Object> r = new ApiResult<Object>();
		r.setSuccess(true);
		r.setCode(ResultCode.SUCCESS.getCode());
		r.setMessage(msg);
		return r;
	}

	@Deprecated
	public static ApiResult<Object> ok(Object data) {
		ApiResult<Object> r = new ApiResult<Object>();
		r.setSuccess(true);
		r.setCode(ResultCode.SUCCESS.getCode());
		r.setResult(data);
		return r;
	}

	public static<T> ApiResult<T> OK() {
		ApiResult<T> r = new ApiResult<T>();
		r.setSuccess(true);
		r.setCode(ResultCode.SUCCESS.getCode());
		r.setMessage("成功");
		return r;
	}

	public static<T> ApiResult<T> OK(T data) {
		ApiResult<T> r = new ApiResult<T>();
		r.setSuccess(true);
		r.setCode(ResultCode.SUCCESS.getCode());
		r.setResult(data);
		return r;
	}

	public static<T> ApiResult<T> OK(String msg, T data) {
		ApiResult<T> r = new ApiResult<T>();
		r.setSuccess(true);
		r.setCode(ResultCode.SUCCESS.getCode());
		r.setMessage(msg);
		r.setResult(data);
		return r;
	}
	
	public static ApiResult<Object> error(String msg) {
		return error(ResultCode.SERVER_ERROR.getCode(), msg);
	}
	
	public static ApiResult<Object> error(int code, String msg) {
		ApiResult<Object> r = new ApiResult<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}

	public ApiResult<T> error500(String message) {
		this.message = message;
		this.code = ResultCode.SERVER_ERROR.getCode();
		this.success = false;
		return this;
	}
	/**
	 * 无权限访问返回结果
	 */
	public static ApiResult<Object> noauth(String msg) {
		return error(ResultCode.NO_AUTHZ.getCode(), msg);
	}

	@JsonIgnore
	private String onlTable;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getOnlTable() {
		return onlTable;
	}

	public void setOnlTable(String onlTable) {
		this.onlTable = onlTable;
	}
}