package com.melink.open.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OpenPlatform implements Serializable {
    private String guid;

    private String name;

    private String description;

    private String website;

    private Date createtime;

    private String icon;

    private Date updatetime;

    @JsonIgnore
    private List<OpenApp> openApps;

    private String secret;

    private Boolean isactive;

    public OpenPlatform() {
        this.guid = GUIDGenerator.generate();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public List<OpenApp> getOpenApps() {
        return openApps;
    }

    public void setOpenApps(List<OpenApp> openApps) {
        this.openApps = openApps;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}