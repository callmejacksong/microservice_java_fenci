package com.melink.sop.api.models.open.modelinfos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PicturePromotionInfo extends BaseModeInfo {

	private String guid;
	@JsonProperty(value = "promotion_url")
	private String promotionUrl;
	@JsonProperty(value = "promotion_begin_time")
	private Date promotionBeginTime;
	@JsonProperty(value = "promotion_end_time")
	private Date promotionEndTime;
	@JsonProperty(value = "promotion_icon_id")
	private String promotionIconId;

	private Date createtime;
	@JsonProperty(value = "promotion_type")
	private String promotionType;
	@JsonProperty(value = "promotion_text")
	private String promotionText;
	@JsonProperty(value = "promotion_video_id")
	private String promotionVideoId;
	@JsonProperty(value = "promotion_icon")
	private String promotionIcon;
	@JsonProperty(value = "np_id")
	private String npId;
	@JsonProperty(value = "promotion_text_color")
	private String promotionTextColor;

	private Integer position;

	public PicturePromotionInfo() {
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getPromotionUrl() {
		return promotionUrl;
	}

	public void setPromotionUrl(String promotionUrl) {
		this.promotionUrl = promotionUrl;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	public String getPromotionIcon() {
		return promotionIcon;
	}

	public void setPromotionIcon(String promotionIcon) {
		this.promotionIcon = promotionIcon;
	}

	public String getPromotionText() {
		return promotionText;
	}

	public void setPromotionText(String promotionText) {
		this.promotionText = promotionText;
	}

	public String getNpId() {
		return npId;
	}

	public void setNpId(String npId) {
		this.npId = npId;
	}

	public String getPromotionTextColor() {
		return promotionTextColor;
	}

	public void setPromotionTextColor(String promotionTextColor) {
		this.promotionTextColor = promotionTextColor;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}
