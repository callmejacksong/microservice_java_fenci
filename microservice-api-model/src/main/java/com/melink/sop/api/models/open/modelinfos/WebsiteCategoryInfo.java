package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melink.sop.api.models.BaseModeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebsiteCategoryInfo extends BaseModeInfo {

	private static final long serialVersionUID = -949021829425565433L;
	private String text;

	private String background;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}
}
