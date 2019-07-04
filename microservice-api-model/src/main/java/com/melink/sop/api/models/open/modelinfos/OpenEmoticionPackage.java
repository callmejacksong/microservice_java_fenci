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
public class OpenEmoticionPackage extends BaseModeInfo {

	private static final long serialVersionUID = 6410185044656688824L;

	private String id;
	private String name;
	private String banner;
	private String intro;
	@JsonProperty(value = "preload_icon")
	private String preloadIcon;
	@JsonProperty(value = "emoji_count")
	private Long emojiCount;
	private String size;
	private String copyright;
	private Date updatedtime;
	@JsonProperty(value = "chat_icon")
	private String chatIcon;
	private String cover;
	@JsonProperty(value = "is_animated")
	private Integer isAnimated;
	@JsonProperty(value = "is_new")
	private Integer isNew;
	private List<OpenEmoticionShort> emojis;
	@JsonIgnore
	private boolean sslRes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBanner() {
		if(banner == null){
			return banner;
		}
		if (banner != null && sslRes) {
			return banner.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
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

	public String getPreloadIcon() {
		if(preloadIcon == null){
			return preloadIcon;
		}
		if (preloadIcon != null && sslRes) {
			return preloadIcon.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
		} else {
			return preloadIcon.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
		}
	}

	public void setPreloadIcon(String preloadIcon) {
		this.preloadIcon = preloadIcon;
	}

	public Long getEmojiCount() {
		return emojiCount;
	}

	public void setEmojiCount(Long emojiCount) {
		this.emojiCount = emojiCount;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public Date getUpdatedtime() {
		return updatedtime;
	}

	public void setUpdatedtime(Date updatedtime) {
		this.updatedtime = updatedtime;
	}

	public String getChatIcon() {
		if(chatIcon == null){
			return chatIcon;
		}
		if (chatIcon != null && sslRes) {
			return chatIcon.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
		} else {
			return chatIcon.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
		}
	}

	public void setChatIcon(String chatIcon) {
		this.chatIcon = chatIcon;
	}

	public String getCover() {
		if(cover == null){
			return cover;
		}
		if (cover != null && sslRes) {
			return cover.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
		} else {
			return cover.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
		}
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getIsAnimated() {
		return isAnimated;
	}

	public void setIsAnimated(Integer isAnimated) {
		this.isAnimated = isAnimated;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public List<OpenEmoticionShort> getEmojis() {
		return emojis;
	}

	public void setEmojis(List<OpenEmoticionShort> emojis) {
		this.emojis = emojis;
	}

	public boolean isSslRes() {
		return sslRes;
	}

	public void setSslRes(boolean sslRes) {
		this.sslRes = sslRes;
	}

}
