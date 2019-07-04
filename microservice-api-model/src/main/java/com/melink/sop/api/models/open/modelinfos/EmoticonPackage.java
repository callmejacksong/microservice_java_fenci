package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseModeInfo;
import com.melink.sop.api.models.QiNiuBucket;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmoticonPackage extends BaseModeInfo {

    private static final long serialVersionUID = 6410185044656688824L;

    private String guid;
    private String name;
    private String banner;
    private String intro;
    private String briefIntro;
    private String copyright;
    private String author;
    private Date createtime;
    private Date updatetime;
    @JsonProperty(value = "display_order")
    private Integer displayOrder;
    private List<Emoticon> emoticons;
    private String delEmotions;
    private String type;
    @JsonProperty(value = "chat_icon")
    private String chatIcon;
    private String cover;
    private String category;

    private Integer promotion;
    @JsonProperty(value = "sdk_type")
    private String sdkType;
    @JsonProperty(value = "md5_code")
    private String md5Code;
    @JsonProperty(value = "is_emoji")
    private Boolean isEmoji;
    @JsonProperty(value = "recommend_pic")
    private String recommendPic;
    private String preload;
    private String source;
    private String price;
    @JsonProperty(value = "sale_area")
    private String saleArea;
    private String industry;

    @JsonProperty("promotion_begin_time")
    private Date promotionBeginTime;

    @JsonProperty("promotion_end_time")
    private Date promotionEndTime;

    @JsonProperty("promotion_begin_time_str")
    private String promotionBeginTimeStr;

    @JsonProperty("promotion_end_time_str")
    private String promotionEndTimeStr;

    @JsonProperty("promotion_id")
    private String promotionId;

    @JsonProperty("promotion_url")
    private String promotionUrl;

    @JsonProperty("promotion_type")
    private String promotionType;

    @JsonProperty("promotion_video_id")
    private String promotionVideoId;

    @JsonProperty("promotion_text")
    private String promotionText;

    @JsonIgnore
    private boolean sslRes;

    @JsonProperty("emoji_count")
    private Integer emojiCount;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getBanner() {
        if (banner == null) {
            return banner;
        }
        if (banner != null && sslRes) {
            banner = banner.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            banner = banner.replace(QiNiuBucket.BQMM_NEW_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            return banner;
        } else {
            return banner.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
        }
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<Emoticon> getEmoticons() {
        return emoticons;
    }

    public void setEmoticons(List<Emoticon> emoticons) {
        this.emoticons = emoticons;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChatIcon() {
        if (chatIcon == null) {
            return chatIcon;
        }
        if (chatIcon != null && sslRes) {
            chatIcon = chatIcon.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            chatIcon = chatIcon.replace(QiNiuBucket.BQMM_NEW_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            return chatIcon;
        } else {
            return chatIcon.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
        }
    }

    public void setChatIcon(String chatIcon) {
        this.chatIcon = chatIcon;
    }

    public String getCover() {
        if (cover == null) {
            return cover;
        }
        if (cover != null && sslRes) {
            cover = cover.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            cover = cover.replace(QiNiuBucket.BQMM_NEW_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            return cover;
        } else {
            return cover.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
        }
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDelEmotions() {
        return delEmotions;
    }

    public void setDelEmotions(String delEmotions) {
        this.delEmotions = delEmotions;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public String getSdkType() {
        return sdkType;
    }

    public void setSdkType(String sdkType) {
        this.sdkType = sdkType;
    }

    public Boolean getIsEmoji() {
        return isEmoji;
    }

    public void setIsEmoji(Boolean isEmoji) {
        this.isEmoji = isEmoji;
    }

    public String getRecommendPic() {
        if (recommendPic == null) {
            return recommendPic;
        }
        if (recommendPic != null && sslRes) {
            recommendPic = recommendPic.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            recommendPic = recommendPic.replace(QiNiuBucket.BQMM_NEW_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            return recommendPic;
        } else {
            return recommendPic.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
        }
    }

    public void setRecommendPic(String recommendPic) {
        this.recommendPic = recommendPic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    public String getPreload() {
        if (preload == null) {
            return preload;
        }
        if (preload != null && sslRes) {
            preload = preload.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            preload = preload.replace(QiNiuBucket.BQMM_NEW_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
            return preload;
        } else {
            return preload.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
        }
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

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionUrl() {
        return promotionUrl;
    }

    public void setPromotionUrl(String promotionUrl) {
        this.promotionUrl = promotionUrl;
    }

    public String getPromotionBeginTimeStr() {
        return promotionBeginTimeStr;
    }

    public void setPromotionBeginTimeStr(String promotionBeginTimeStr) {
        this.promotionBeginTimeStr = promotionBeginTimeStr;
    }

    public String getPromotionEndTimeStr() {
        return promotionEndTimeStr;
    }

    public void setPromotionEndTimeStr(String promotionEndTimeStr) {
        this.promotionEndTimeStr = promotionEndTimeStr;
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

    public Integer getEmojiCount() {
        return emojiCount;
    }

    public void setEmojiCount(Integer emojiCount) {
        this.emojiCount = emojiCount;
    }
}