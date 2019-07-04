package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetKeywordInfo extends BaseModeInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8888745038496607053L;
	private String guid;
	private String text;
	private int theme;
	private int chat;
	private int common;
	private int alipay;

	@JsonProperty("ip_activity")
	private boolean ipActivity;
	private List<OpenEmoticion> emoticions;

	private List<OpenEmoticionMini> emoticons;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	public int getChat() {
		return chat;
	}

	public void setChat(int chat) {
		this.chat = chat;
	}

	public int getCommon() {
		return common;
	}

	public void setCommon(int common) {
		this.common = common;
	}

	public int getAlipay() {
		return alipay;
	}

	public void setAlipay(int alipay) {
		this.alipay = alipay;
	}

	public List<OpenEmoticion> getEmoticions() {
		return emoticions;
	}

	public void setEmoticions(List<OpenEmoticion> emoticions) {
		this.emoticions = emoticions;
	}

	public List<OpenEmoticionMini> getEmoticons() {
		return emoticons;
	}

	public void setEmoticons(List<OpenEmoticionMini> emoticons) {
		this.emoticons = emoticons;
	}

	public boolean getIpActivity() {
		return ipActivity;
	}

	public void setIpActivity(boolean ipActivity) {
		this.ipActivity = ipActivity;
	}
}
