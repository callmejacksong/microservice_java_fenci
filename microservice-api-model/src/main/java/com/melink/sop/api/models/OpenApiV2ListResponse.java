package com.melink.sop.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenApiV2ListResponse<T> implements Serializable {

    private List<T> data;

    private OpenApiPagination pagination;

    private int status;

    private String msg;
    public OpenApiV2ListResponse(){}

    public OpenApiV2ListResponse(int status) {
        this.status = status;
    }

    public OpenApiV2ListResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public OpenApiV2ListResponse(int status, List<T> data, OpenApiPagination pagination) {
        this.data = data;
        this.pagination = pagination;
        this.status = status;
    }

    public OpenApiV2ListResponse(List<T> data, OpenApiPagination pagination, int status, String msg) {
        this.data = data;
        this.pagination = pagination;
        this.status = status;
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public OpenApiPagination getPagination() {
        return pagination;
    }

    public void setPagination(OpenApiPagination pagination) {
        this.pagination = pagination;
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
}