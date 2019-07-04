package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the open_app database table.
 */
public class OpenApp implements Serializable {
    private static final long serialVersionUID = 9029619718481816849L;

    private String guid;

    private String appEname;

    private String appName;

    private String appsecret;

    private Date createtime;

    private String icon;

    private Date updatetime;

    private List<OpenAppProp> openAppProps;

    private List<OpenAppCategory> openAppCategory;

    private String platformId;

    private OpenPlatform openPlatform;

    private Boolean isactive;

    private Boolean isshow;

    private String externalId;

    private String registrantName;

    private String registrantCompany;

    private String registrantEmail;

    private String registrantQQ;

    private Date expiretime;

    private String nKey;

    private Integer type;

    private Boolean copyrightPermisson;

    private Boolean promotionSwitch;

    private Boolean globalPromotionSwitch;

    public OpenApp() {
        this.guid = GUIDGenerator.generate();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public List<OpenAppCategory> getOpenAppCategory() {
        return openAppCategory;
    }

    public void setOpenAppCategory(List<OpenAppCategory> openAppCategory) {
        this.openAppCategory = openAppCategory;
    }

    public String getAppEname() {
        return this.appEname;
    }

    public void setAppEname(String appEname) {
        this.appEname = appEname;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppsecret() {
        return this.appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public List<OpenAppProp> getOpenAppProps() {
        return openAppProps;
    }

    public void setOpenAppProps(List<OpenAppProp> openAppProps) {
        this.openAppProps = openAppProps;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public OpenPlatform getOpenPlatform() {
        return openPlatform;
    }

    public void setOpenPlatform(OpenPlatform openPlatform) {
        this.openPlatform = openPlatform;
    }

    public Boolean getIsshow() {
        return isshow;
    }

    public void setIsshow(Boolean isshow) {
        this.isshow = isshow;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getRegistrantName() {
        return registrantName;
    }

    public void setRegistrantName(String registrantName) {
        this.registrantName = registrantName;
    }

    public String getRegistrantCompany() {
        return registrantCompany;
    }

    public void setRegistrantCompany(String registrantCompany) {
        this.registrantCompany = registrantCompany;
    }

    public String getRegistrantEmail() {
        return registrantEmail;
    }

    public void setRegistrantEmail(String registrantEmail) {
        this.registrantEmail = registrantEmail;
    }

    public String getRegistrantQQ() {
        return registrantQQ;
    }

    public void setRegistrantQQ(String registrantQQ) {
        this.registrantQQ = registrantQQ;
    }

    public Date getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(Date expiretime) {
        this.expiretime = expiretime;
    }

    public String getnKey() {
        return nKey;
    }

    public void setnKey(String nKey) {
        this.nKey = nKey;
    }

    public Boolean getCopyrightPermisson() {
        return copyrightPermisson;
    }

    public void setCopyrightPermisson(Boolean copyrightPermisson) {
        this.copyrightPermisson = copyrightPermisson;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getPromotionSwitch() {
        return promotionSwitch;
    }

    public void setPromotionSwitch(Boolean promotionSwitch) {
        this.promotionSwitch = promotionSwitch;
    }

    public Boolean getGlobalPromotionSwitch() {
        return globalPromotionSwitch;
    }

    public void setGlobalPromotionSwitch(Boolean globalPromotionSwitch) {
        this.globalPromotionSwitch = globalPromotionSwitch;
    }
}