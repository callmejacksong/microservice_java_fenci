package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenKeywordEmojiInfo extends BaseModeInfo {
	private static final long serialVersionUID = 259467681319984812L;

	private String keyword;

	private List<OpenEmoticion> emojis;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<OpenEmoticion> getEmojis() {
		return emojis;
	}

	public void setEmojis(List<OpenEmoticion> emojis) {
		this.emojis = emojis;
	}

}