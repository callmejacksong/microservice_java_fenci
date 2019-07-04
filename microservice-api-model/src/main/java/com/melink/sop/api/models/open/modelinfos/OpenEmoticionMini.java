package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseModeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenEmoticionMini extends BaseModeInfo {

	private static final long serialVersionUID = -1167859636624629585L;
	private String guid;
	private String thumb;
	private String main;
	private Integer width;
	private Integer height;
	@JsonProperty(value = "is_animated")
	private String isAnimated;
	@JsonProperty(value = "fsize")
	private Integer fs;
	private String md5;
	private Integer type;  //模板图类型,换脸,换字
	@JsonProperty(value = "model_id")
	private String modelId;
	@JsonProperty(value = "model_url")
	private String modelUrl;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		OpenEmoticionMini emoticion = (OpenEmoticionMini) obj;
        return main.equals(emoticion.main);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getIsAnimated() {
		return isAnimated;
	}

	public void setIsAnimated(String isAnimated) {
		this.isAnimated = isAnimated;
	}

	public Integer getFs() {
		return fs;
	}

	public void setFs(Integer fs) {
		this.fs = fs;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelUrl() {
		return modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}
}