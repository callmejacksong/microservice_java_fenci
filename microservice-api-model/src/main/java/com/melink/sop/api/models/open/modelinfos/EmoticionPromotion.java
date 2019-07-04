package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseModeInfo;
import com.melink.sop.api.models.QiNiuBucket;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmoticionPromotion extends BaseModeInfo {

    private String guid;
    @JsonProperty(value = "emo_code")
    private String emoCode;

    @JsonIgnore
    private boolean sslRes;

    @JsonProperty("promotion_begin_time")
    private Long promotionBeginTime;

    @JsonProperty("promotion_end_time")
    private Long promotionEndTime;

    @JsonProperty("promotion_url")
    private String promotionUrl;

    @JsonProperty("promotion_icon")
    private String promotionIcon;

    @JsonProperty("promotion_type")
    private String promotionType;

    @JsonProperty("promotion_video_title")
    private String promotionVideoTitle;

    @JsonProperty("promotion_video_author_name")
    private String promotionVideoAuthorName;

    @JsonProperty("promotion_video_author_icon")
    private String promotionVideoAuthorIcon;

    @JsonProperty("promotion_video_description")
    private String promotionVideoDsecription;

    @JsonProperty("promotion_video_width")
    private Integer promotionVideoWidth;

    @JsonProperty("promotion_video_height")
    private Integer promotionVideoHeight;

    @JsonProperty("promotion_text_list")
    private List<String> promotionTextList;

    @JsonProperty("promotion_text_color")
    private String promotionTextColor;

    @JsonProperty("promotion_video_detail_url")
    private String promotionVideoDetailUrl;

    @JsonIgnore
    private Integer position;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getEmoCode() {
        return emoCode;
    }

    public void setEmoCode(String emoCode) {
        this.emoCode = emoCode;
    }

    public Long getPromotionBeginTime() {
        return promotionBeginTime;
    }

    public void setPromotionBeginTime(Long promotionBeginTime) {
        this.promotionBeginTime = promotionBeginTime;
    }

    public Long getPromotionEndTime() {
        return promotionEndTime;
    }

    public void setPromotionEndTime(Long promotionEndTime) {
        this.promotionEndTime = promotionEndTime;
    }

    public String getPromotionUrl() {
        if (promotionUrl == null) {
            return promotionUrl;
        }
        if (promotionUrl != null && sslRes) {
            promotionUrl = promotionUrl.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            promotionUrl = promotionUrl.replace(QiNiuBucket.BQMM_NEW_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            return promotionUrl;
        } else {
            return promotionUrl;
        }
    }

    public void setPromotionUrl(String promotionUrl) {
        this.promotionUrl = promotionUrl;
    }

    public String getPromotionIcon() {
        if (promotionIcon == null) {
            return promotionIcon;
        }
        if (promotionIcon != null && sslRes) {
            promotionIcon = promotionIcon.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            promotionIcon = promotionIcon.replace(QiNiuBucket.BQMM_NEW_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            return promotionIcon;
        } else {
            return promotionIcon;
        }
    }

    public void setPromotionIcon(String promotionIcon) {
        this.promotionIcon = promotionIcon;
    }

    public boolean isSslRes() {
        return sslRes;
    }

    public void setSslRes(boolean sslRes) {
        this.sslRes = sslRes;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public List<String> getPromotionTextList() {
        return promotionTextList;
    }

    public void setPromotionTextList(List<String> promotionTextList) {
        this.promotionTextList = promotionTextList;
    }

    public String getPromotionVideoTitle() {
        return promotionVideoTitle;
    }

    public void setPromotionVideoTitle(String promotionVideoTitle) {
        this.promotionVideoTitle = promotionVideoTitle;
    }

    public String getPromotionVideoAuthorName() {
        return promotionVideoAuthorName;
    }

    public void setPromotionVideoAuthorName(String promotionVideoAuthorName) {
        this.promotionVideoAuthorName = promotionVideoAuthorName;
    }

    public String getPromotionVideoAuthorIcon() {
        return promotionVideoAuthorIcon;
    }

    public void setPromotionVideoAuthorIcon(String promotionVideoAuthorIcon) {
        this.promotionVideoAuthorIcon = promotionVideoAuthorIcon;
    }

    public String getPromotionVideoDsecription() {
        return promotionVideoDsecription;
    }

    public void setPromotionVideoDsecription(String promotionVideoDsecription) {
        this.promotionVideoDsecription = promotionVideoDsecription;
    }

    public Integer getPromotionVideoWidth() {
        return promotionVideoWidth;
    }

    public void setPromotionVideoWidth(Integer promotionVideoWidth) {
        this.promotionVideoWidth = promotionVideoWidth;
    }

    public Integer getPromotionVideoHeight() {
        return promotionVideoHeight;
    }

    public void setPromotionVideoHeight(Integer promotionVideoHeight) {
        this.promotionVideoHeight = promotionVideoHeight;
    }

    public String getPromotionTextColor() {
        return promotionTextColor;
    }

    public void setPromotionTextColor(String promotionTextColor) {
        this.promotionTextColor = promotionTextColor;
    }

    public String getPromotionVideoDetailUrl() {
        return promotionVideoDetailUrl;
    }

    public void setPromotionVideoDetailUrl(String promotionVideoDetailUrl) {
        this.promotionVideoDetailUrl = promotionVideoDetailUrl;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}