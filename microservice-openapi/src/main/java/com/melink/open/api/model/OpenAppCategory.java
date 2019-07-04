package com.melink.open.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;

/**
 * The persistent class for the basic_emoticon_package database table.
 */
public class OpenAppCategory implements Serializable {
    private String guid;

    private String categoryId;

    @JsonIgnore
    private BasicEmoticonCategory emoCategory;

    private String appId;

    @JsonIgnore
    private OpenApp openApp;

    private Integer displayOrder;

    public OpenAppCategory() {
        this.guid = GUIDGenerator.generate();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public BasicEmoticonCategory getEmoCategory() {
        return emoCategory;
    }

    public void setEmoCategory(BasicEmoticonCategory emoCategory) {
        this.emoCategory = emoCategory;
    }

    public OpenApp getOpenApp() {
        return openApp;
    }

    public void setOpenApp(OpenApp openApp) {
        this.openApp = openApp;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

}