package com.melink.open.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the basic_emoticon_package database table.
 */
public class BasicEmoticonPackage implements Serializable {
    private String guid;

    private String author;

    private String banner;

    private String cover;

    private String chatIcon;

    private String copyright;

    private Date createtime;

    private String intro;

    private String briefIntro;

    private String name;

    private Date updatetime;

    private Integer displayOrder;

    private List<BasicEmoticon> basicEmoticons;

    @JsonIgnore
    private List<BasicEmoticonCategoryPackage> categoryPackage;

    private String type;

    private Integer promotion;

    private Integer sdkType;

    private String md5Code;

    private Boolean isEmoji;

    private String recommendPic;

    private String preload;

    private String price;

    private String saleArea;

    private String industry;

    private boolean showCopyright;

    private Integer emojiCount;

    private String source;

    public BasicEmoticonPackage(){
        this.guid = GUIDGenerator.generate();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSaleArea() {
        return saleArea;
    }

    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBanner() {
        return this.banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getCopyright() {
        return this.copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIntro() {
        return this.intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public List<BasicEmoticon> getBasicEmoticons() {
        return basicEmoticons;
    }

    public void setBasicEmoticons(List<BasicEmoticon> basicEmoticons) {
        this.basicEmoticons = basicEmoticons;
    }

    public List<BasicEmoticonCategoryPackage> getCategoryPackage() {
        return categoryPackage;
    }

    public void setCategoryPackage(List<BasicEmoticonCategoryPackage> categoryPackage) {
        this.categoryPackage = categoryPackage;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getChatIcon() {
        return chatIcon;
    }

    public void setChatIcon(String chatIcon) {
        this.chatIcon = chatIcon;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public Boolean getIsEmoji() {
        return isEmoji;
    }

    public void setIsEmoji(Boolean isEmoji) {
        this.isEmoji = isEmoji;
    }

    public Integer getSdkType() {
        return sdkType;
    }

    public void setSdkType(Integer sdkType) {
        this.sdkType = sdkType;
    }

    public String getRecommendPic() {
        return recommendPic;
    }

    public void setRecommendPic(String recommendPic) {
        this.recommendPic = recommendPic;
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    public String getPreload() {
        return preload;
    }

    public void setPreload(String preload) {
        this.preload = preload;
    }

    public String getBriefIntro() {
        return briefIntro;
    }

    public void setBriefIntro(String briefIntro) {
        this.briefIntro = briefIntro;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public boolean isShowCopyright() {
        return showCopyright;
    }

    public void setShowCopyright(boolean showCopyright) {
        this.showCopyright = showCopyright;
    }

    public Integer getEmojiCount() {
        return emojiCount;
    }

    public void setEmojiCount(Integer emojiCount) {
        this.emojiCount = emojiCount;
    }
}