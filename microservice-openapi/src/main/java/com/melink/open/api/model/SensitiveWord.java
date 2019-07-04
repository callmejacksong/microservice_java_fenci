package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;

public class SensitiveWord implements Serializable {
    private static final long serialVersionUID = -1683781196920213591L;

    private String guid;

    private String text;

    private Integer type;

    private String appId;

    public SensitiveWord() {
        this.guid = GUIDGenerator.generate();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}