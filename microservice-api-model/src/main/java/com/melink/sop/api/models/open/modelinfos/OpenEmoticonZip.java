package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenEmoticonZip extends BaseModeInfo {


	private static final long serialVersionUID = -8263219035225171642L;
	private String id;
	private String name;
	private String brief;
	private String description;
	private String zip;
	private String copyright;
	private String banner;
	private String cover;
	@JsonProperty("chat_icon")
	private String chatIcon;
	@JsonProperty("sticker_count")
	private Integer stickerCount;
	@JsonProperty("create_time")
	private Date createtime;

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

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
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

	public Integer getStickerCount() {
		return stickerCount;
	}

	public void setStickerCount(Integer stickerCount) {
		this.stickerCount = stickerCount;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
