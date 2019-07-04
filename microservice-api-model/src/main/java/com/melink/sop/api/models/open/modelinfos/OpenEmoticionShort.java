package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melink.sop.api.models.BaseModeInfo;
import com.melink.sop.api.models.QiNiuBucket;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenEmoticionShort extends BaseModeInfo {

	private static final long serialVersionUID = -1167859636624629585L;
	private String text;
	private String code;
	private String thumb;
	private String main;
	@JsonIgnore
	private boolean sslRes;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getThumb() {
		if(thumb == null){
			return thumb;
		}
		if (thumb != null && sslRes) {
			return thumb.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
		} else {
			return thumb.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
		}
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getMain() {
		if(main == null){
			return main;
		}
		if (main != null && sslRes) {
			return main.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_HTTPS_BUCKET);
		} else {
			return main.replace(QiNiuBucket.BQMM_BUCKET, QiNiuBucket.BQMM_NEW_BUCKET);
		}
	}

	public void setMain(String main) {
		this.main = main;
	}

	public boolean isSslRes() {
		return sslRes;
	}

	public void setSslRes(boolean sslRes) {
		this.sslRes = sslRes;
	}

}