package com.melink.sop.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenApiV2DataResponse<T> implements Serializable {

    private T data;

    private int status;

    private String msg;

    public OpenApiV2DataResponse(){}

    public OpenApiV2DataResponse(int status) {
        this.status = status;
    }

    public OpenApiV2DataResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public OpenApiV2DataResponse(int status, T data) {
        this.data = data;
        this.status = status;
    }

    public OpenApiV2DataResponse(int status, T data, String msg) {
        this.data = data;
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}