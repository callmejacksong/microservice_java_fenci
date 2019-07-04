package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseModeInfo;
import com.melink.sop.api.models.QiNiuBucket;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Emoticon extends BaseModeInfo {

    private static final long serialVersionUID = -1167859636624629585L;
    private String guid;
    @JsonProperty(value = "emo_text")
    private String emoText;
    @JsonProperty(value = "emo_code")
    private String emoCode;
    private String thumbail;
    @JsonProperty(value = "main_image")
    private String mainImage;
    @JsonProperty(value = "is_emoji")
    private Boolean isEmoji;
    @JsonProperty(value = "display_order")
    private Integer displayOrder;
    @JsonProperty(value = "package_id")
    private String packageId;
    @JsonProperty(value = "sdk_type")
    private String sdkType;
    private String source;
    private String industry;
    @JsonProperty(value = "is_trend")
    private Boolean isTrend;
    @JsonIgnore
    private boolean sslRes;

    @JsonProperty("promotion_begin_time")
    private Date promotionBeginTime;

    @JsonProperty("promotion_end_time")
    private Date promotionEndTime;

    @JsonProperty("promotion_icon_id")
    private String promotionIconId;

    @JsonProperty("promotion_url")
    private String promotionUrl;

    @JsonProperty("promotion_icon")
    private String promotionIcon;

    @JsonProperty("promotion_type")
    private String promotionType;

    @JsonProperty("promotion_video_id")
    private String promotionVideoId;

    @JsonProperty("promotion_text")
    private String promotionText;

    public String getSdkType() {
        return sdkType;
    }

    public void setSdkType(String sdkType) {
        this.sdkType = sdkType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getEmoText() {
        return emoText;
    }

    public void setEmoText(String emoText) {
        this.emoText = emoText;
    }

    public String getThumbail() {
        if (thumbail == null) {
            return thumbail;
        }
        if (thumbail != null && sslRes) {
            thumbail = thumbail.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            thumbail = thumbail.replace(QiNiuBucket.BQMM_NEW_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            return thumbail;
        } else {
            return thumbail.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
        }
    }

    public void setThumbail(String thumbail) {
        this.thumbail = thumbail;
    }

    public String getMainImage() {
        if (mainImage == null) {
            return mainImage;
        }
        if (mainImage != null && sslRes) {
            mainImage = mainImage.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            mainImage = mainImage.replace(QiNiuBucket.BQMM_NEW_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            return mainImage;
        } else {
            return mainImage;
        }
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
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

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
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

    public boolean isSslRes() {
        return sslRes;
    }

    public void setSslRes(boolean sslRes) {
        this.sslRes = sslRes;
    }

    public Date getPromotionBeginTime() {
        return promotionBeginTime;
    }

    public void setPromotionBeginTime(Date promotionBeginTime) {
        this.promotionBeginTime = promotionBeginTime;
    }

    public Date getPromotionEndTime() {
        return promotionEndTime;
    }

    public void setPromotionEndTime(Date promotionEndTime) {
        this.promotionEndTime = promotionEndTime;
    }

    public String getPromotionIconId() {
        return promotionIconId;
    }

    public void setPromotionIconId(String promotionIconId) {
        this.promotionIconId = promotionIconId;
    }

    public String getPromotionUrl() {
        return promotionUrl;
    }

    public void setPromotionUrl(String promotionUrl) {
        this.promotionUrl = promotionUrl;
    }

    public String getPromotionIcon() {
        return promotionIcon;
    }

    public void setPromotionIcon(String promotionIcon) {
        this.promotionIcon = promotionIcon;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getPromotionVideoId() {
        return promotionVideoId;
    }

    public void setPromotionVideoId(String promotionVideoId) {
        this.promotionVideoId = promotionVideoId;
    }

    public String getPromotionText() {
        return promotionText;
    }

    public void setPromotionText(String promotionText) {
        this.promotionText = promotionText;
    }

}