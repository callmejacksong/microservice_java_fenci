package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the basic_emoticon_package database table.
 */
public class BasicEmoticonCategory implements Serializable {
    private String guid;

    private String name;

    private Boolean isActive;

    private Date createtime;

    private Date updatetime;

    private Integer displayOrder;

    private Integer sdkType;

    private String codeField;

    private List<BasicEmoticonCategoryPackage> categoryPackage;

    private List<OpenAppCategory> openAppCategory;

    public BasicEmoticonCategory() {
        this.guid = GUIDGenerator.generate();
    }

    public List<OpenAppCategory> getOpenAppCategory() {
        return openAppCategory;
    }

    public void setOpenAppCategory(List<OpenAppCategory> openAppCategory) {
        this.openAppCategory = openAppCategory;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<BasicEmoticonCategoryPackage> getCategoryPackage() {
        return categoryPackage;
    }

    public void setCategoryPackage(
            List<BasicEmoticonCategoryPackage> categoryPackage) {
        this.categoryPackage = categoryPackage;
    }

    public String getCodeField() {
        return codeField;
    }

    public void setCodeField(String codeField) {
        this.codeField = codeField;
    }

    public Integer getSdkType() {
        return sdkType;
    }

    public void setSdkType(Integer sdkType) {
        this.sdkType = sdkType;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}