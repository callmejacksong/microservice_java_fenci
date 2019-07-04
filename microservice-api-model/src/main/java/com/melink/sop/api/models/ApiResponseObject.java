package com.melink.sop.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseObject<T> implements Serializable {

	private static final long serialVersionUID = -1L;

	@JsonProperty(value = "error_code")
	private Integer errorCode;
	private String message;
	private T data;
	@JsonProperty(value = "data_list")
	private List<T> dataList;

	// 部分接口返回缓存的过期时间
	@JsonProperty(value = "expires_in")
	private Integer expiresIn;

	private Integer count;

	public ApiResponseObject() {

	}

	public ApiResponseObject(Integer errorCode, String msg) {
		this.errorCode = errorCode;
		this.message = msg;
	}

	public ApiResponseObject(Integer errorCode, String msg, T data) {
		this.errorCode = errorCode;
		this.message = msg;
		this.data = data;
	}

	public ApiResponseObject(Integer errorCode) {
		this.errorCode = errorCode;
		this.data = null;
	}

	public ApiResponseObject(Integer errorCode, String msg, List<T> dataList) {
		this.errorCode = errorCode;
		this.message = msg;
		this.dataList = dataList;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String msg) {
		this.message = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}