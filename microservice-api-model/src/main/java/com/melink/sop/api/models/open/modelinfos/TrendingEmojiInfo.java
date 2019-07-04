package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendingEmojiInfo extends BaseModeInfo {
	private static final long serialVersionUID = 259467681319984812L;

	private int update;

	private List<OpenEmoticion> emojis;

	public List<OpenEmoticion> getEmojis() {
		return emojis;
	}

	public void setEmojis(List<OpenEmoticion> emojis) {
		this.emojis = emojis;
	}

	public int getUpdate() {
		return update;
	}

	public void setUpdate(int update) {
		this.update = update;
	}

}