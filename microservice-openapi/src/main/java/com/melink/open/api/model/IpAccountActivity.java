package com.melink.open.api.model;

import java.io.Serializable;
import java.util.Date;

public class IpAccountActivity implements Serializable {

    private Integer id;

    private String ipId;

    private String title;

    private String brief;

    private String banner;

    private String ruleImg;

    private String winningUser;

    private String beginTime;

    private String endTime;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getipId() {
        return ipId;
    }

    public void setipId(String ipId) {
        this.ipId = ipId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getRuleImg() {
        return ruleImg;
    }

    public void setRuleImg(String ruleImg) {
        this.ruleImg = ruleImg;
    }

    public String getWinningUser() {
        return winningUser;
    }

    public void setWinningUser(String winningUser) {
        this.winningUser = winningUser;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}