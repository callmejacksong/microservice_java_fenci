package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the basic_emoticon database table.
 */
public class BasicEmoticon implements Serializable {

    private String guid;

    private Integer displayOrder;

    private String emoText;

    private String emoCode;

    private String mainImage;

    private Boolean isEmoji;

    private String packageId;

    private BasicEmoticonPackage basicEmoticonPackage;

    private List<Keyword> keyword;

    private String thumbail;

    private String source;

    private String industry;

    private Integer sdkType;

    private Boolean isTrend;

    private Integer trendOrder;

    private int trendLock;

    private Date updatetime;

    private int fsize;

    private String md5;

    public BasicEmoticon() {
        this.guid = GUIDGenerator.generate();
    }

    public List<Keyword> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<Keyword> keyword) {
        this.keyword = keyword;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getSdkType() {
        return sdkType;
    }

    public void setSdkType(Integer sdkType) {
        this.sdkType = sdkType;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getEmoText() {
        return this.emoText;
    }

    public void setEmoText(String emoText) {
        this.emoText = emoText;
    }

    public String getMainImage() {
        return this.mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getPackageId() {
        return this.packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getThumbail() {
        return this.thumbail;
    }

    public void setThumbail(String thumbail) {
        this.thumbail = thumbail;
    }

    public BasicEmoticonPackage getBasicEmoticonPackage() {
        return basicEmoticonPackage;
    }

    public void setBasicEmoticonPackage(BasicEmoticonPackage basicEmoticonPackage) {
        this.basicEmoticonPackage = basicEmoticonPackage;
    }

    public String getEmoCode() {
        return emoCode;
    }

    public void setEmoCode(String emoCode) {
        this.emoCode = emoCode;
    }

    public Boolean getIsEmoji() {
        return isEmoji;
    }

    public void setIsEmoji(Boolean isEmoji) {
        this.isEmoji = isEmoji;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Boolean getIsTrend() {
        return isTrend;
    }

    public void setIsTrend(Boolean isTrend) {
        this.isTrend = isTrend;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getTrendOrder() {
        return trendOrder;
    }

    public void setTrendOrder(Integer trendOrder) {
        this.trendOrder = trendOrder;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        BasicEmoticon s = (BasicEmoticon) obj;
        return guid.equals(s.guid);
    }

    @Override
    public int hashCode() {
        return guid.hashCode();
    }

    public int getTrendLock() {
        return trendLock;
    }

    public void setTrendLock(int trendLock) {
        this.trendLock = trendLock;
    }

    public int getFsize() {
        return fsize;
    }

    public void setFsize(int fsize) {
        this.fsize = fsize;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}