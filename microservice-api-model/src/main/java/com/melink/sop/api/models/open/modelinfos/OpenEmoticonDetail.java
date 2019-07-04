package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenEmoticonDetail extends BaseModeInfo {

	private static final long serialVersionUID = 4532565448123133645L;
	private String main;
	private Integer width;
	private Integer height;
	@JsonProperty(value = "fsize")
	private Integer fs;
	private String mp4;
	private String less1M;
	private String less2M;

	@JsonProperty(value = "ip_list")
	private List<AccountInfo> ipList;
 	private List<String> keywords;

	private Integer pv;

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

	public Integer getFs() {
		return fs;
	}

	public void setFs(Integer fs) {
		this.fs = fs;
	}

	public String getMp4() {
		return mp4;
	}

	public void setMp4(String mp4) {
		this.mp4 = mp4;
	}

	public String getLess1M() {
		return less1M;
	}

	public void setLess1M(String less1M) {
		this.less1M = less1M;
	}

	public String getLess2M() {
		return less2M;
	}

	public void setLess2M(String less2M) {
		this.less2M = less2M;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<AccountInfo> getIpList() {
		return ipList;
	}

	public void setIpList(List<AccountInfo> ipList) {
		this.ipList = ipList;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}
}