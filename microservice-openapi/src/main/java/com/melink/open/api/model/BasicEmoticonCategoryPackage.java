package com.melink.open.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;

/**
 * The persistent class for the basic_emoticon_package database table.
 */
public class BasicEmoticonCategoryPackage implements Serializable {
    private String guid;

    private String categoryId;

    @JsonIgnore
    private BasicEmoticonCategory emoCategory;

    private String packageId;

    // bi-directional many-to-one association to SysRole
    @JsonIgnore
    private BasicEmoticonPackage emoPackage;

    private Integer displayOrder;

    public BasicEmoticonCategoryPackage() {
        this.guid = GUIDGenerator.generate();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public BasicEmoticonCategory getEmoCategory() {
        return emoCategory;
    }

    public void setEmoCategory(BasicEmoticonCategory emoCategory) {
        this.emoCategory = emoCategory;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public BasicEmoticonPackage getEmoPackage() {
        return emoPackage;
    }

    public void setEmoPackage(BasicEmoticonPackage emoPackage) {
        this.emoPackage = emoPackage;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}