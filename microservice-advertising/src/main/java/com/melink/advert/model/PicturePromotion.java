package com.melink.advert.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;

public class PicturePromotion implements Serializable {

	private String guid;

	private String promotionUrl;

	private Date promotionBeginTime;

	private Date promotionEndTime;

	private String promotionIconId;

	private Date createtime;

	private String promotionType;

	private String promotionText;

	private String promotionVideoId;

	private String promotionIcon;

	private String npId;

	private String promotionTextColor;

	private Integer position;

	public PicturePromotion() {
		this.guid = GUIDGenerator.generate();
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
