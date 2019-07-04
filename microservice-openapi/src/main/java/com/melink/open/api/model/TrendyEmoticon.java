package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by orlan on 2017/1/10.
 */
public class TrendyEmoticon implements Serializable {

    private String guid;
    private String mainImage;
    private Integer trendLock = 0;
    private Date createdtime;
    private Date updatedtime;
    private String showDate;
    private String emoText;
    private String emoCode;
    private String packageId;
    private String copyright;
    private Integer isAnimated;
    private Integer width;
    private Integer height;
    private String appId;
    private String md5;
    private Integer fsize;
    private String thumb;
    private String npId;

    public TrendyEmoticon() {
        this.guid = GUIDGenerator.generate();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getTrendLock() {
        return trendLock;
    }

    public void setTrendLock(Integer trendLock) {
        this.trendLock = trendLock;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Date getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(Date updatedtime) {
        this.updatedtime = updatedtime;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getEmoText() {
        return emoText;
    }

    public void setEmoText(String emoText) {
        this.emoText = emoText;
    }

    public String getEmoCode() {
        return emoCode;
    }

    public void setEmoCode(String emoCode) {
        this.emoCode = emoCode;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Integer getIsAnimated() {
        return isAnimated;
    }

    public void setIsAnimated(Integer isAnimated) {
        this.isAnimated = isAnimated;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getFsize() {
        return fsize;
    }

    public void setFsize(Integer fsize) {
        this.fsize = fsize;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getNpId() {
        return npId;
    }

    public void setNpId(String npId) {
        this.npId = npId;
    }
}
