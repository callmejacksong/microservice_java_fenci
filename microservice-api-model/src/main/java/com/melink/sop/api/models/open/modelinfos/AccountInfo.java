package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountInfo extends BaseModeInfo {

    private static final long serialVersionUID = -7422492693178658341L;

    private String guid;

    private String name;

    private String icon;

    private String description;

    private Boolean verification;

    @JsonProperty("inner_link")
    private String innerLink;
    @JsonProperty("outer_link")
    private String outerLink;

    private String banner;

    private String mp4;

    private String weibo;

    private String officialAccounts;

    private String video;

    private String baike;

    private String doubanMovie;

    private String doubanPage;

    private String miniBanner;

    @JsonProperty("activity_brief")
    private String activityBrief;

    @JsonProperty("first_picture_url")
    private String firstPictureUrl;

    List<OpenEmoticion> emoticionList;

    @JsonProperty("emoticons")
    List<OpenEmoticionMini> emoticionMiniList;

    @JsonProperty("activity_banner")
    private String activiyBanner;

    public AccountInfo() {
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVerification() {
        return verification;
    }

    public void setVerification(Boolean verification) {
        this.verification = verification;
    }

    public List<OpenEmoticion> getEmoticionList() {
        return emoticionList;
    }

    public void setEmoticionList(List<OpenEmoticion> emoticionList) {
        this.emoticionList = emoticionList;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getInnerLink() {
        return innerLink;
    }

    public void setInnerLink(String innerLink) {
        this.innerLink = innerLink;
    }

    public String getOuterLink() {
        return outerLink;
    }

    public void setOuterLink(String outerLink) {
        this.outerLink = outerLink;
    }

    public String getFirstPictureUrl() {
        return firstPictureUrl;
    }

    public void setFirstPictureUrl(String firstPictureUrl) {
        this.firstPictureUrl = firstPictureUrl;
    }

    public List<OpenEmoticionMini> getEmoticionMiniList() {
        return emoticionMiniList;
    }

    public void setEmoticionMiniList(List<OpenEmoticionMini> emoticionMiniList) {
        this.emoticionMiniList = emoticionMiniList;
    }

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getOfficialAccounts() {
        return officialAccounts;
    }

    public void setOfficialAccounts(String officialAccounts) {
        this.officialAccounts = officialAccounts;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getBaike() {
        return baike;
    }

    public void setBaike(String baike) {
        this.baike = baike;
    }

    public String getDoubanMovie() {
        return doubanMovie;
    }

    public void setDoubanMovie(String doubanMovie) {
        this.doubanMovie = doubanMovie;
    }

    public String getDoubanPage() {
        return doubanPage;
    }

    public void setDoubanPage(String doubanPage) {
        this.doubanPage = doubanPage;
    }

    public String getMiniBanner() {
        return miniBanner;
    }

    public void setMiniBanner(String miniBanner) {
        this.miniBanner = miniBanner;
    }

    public String getActivityBrief() {
        return activityBrief;
    }

    public void setActivityBrief(String activityBrief) {
        this.activityBrief = activityBrief;
    }

    public String getActiviyBanner() {
        return activiyBanner;
    }

    public void setActiviyBanner(String activiyBanner) {
        this.activiyBanner = activiyBanner;
    }
}
