package com.melink.open.api.vo;

import com.melink.open.api.constant.SearchTypeConsts;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SearchVO {
	private String p = "1";
	private String size = "20";
	private String q;
	private String fs="";
	private String fs_limit = "";
	private String gt_min = "";
	private String gt_max = "";
	private String gt_crop = "";
	private String ex_fmt = "";
	private String mutliq = "";
	private String type = SearchTypeConsts.HYBRID;

	@Pattern(regexp = "[1-9]\\d*|\\s*", message = "p")
	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	@Pattern(regexp = "[1-9]\\d*|\\s*", message = "size")
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@NotNull(message = "q")
	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

//	@Pattern(regexp = "\\s|(large|medium|small)", message = "fs")
	public String getFs() {
		return fs;
	}

	public void setFs(String fs) {
		this.fs = fs;
	}

	@Pattern(regexp = "[1-9]\\d*|\\s?", message = "fs_limit")
	public String getFs_limit() {
		return fs_limit.trim();
	}

	public void setFs_limit(String fs_limit) {
		this.fs_limit = fs_limit;
	}

	@Pattern(regexp = "[1-9]\\d*|\\s?", message = "gt_min")
	public String getGt_min() {
		return gt_min.trim();
	}

	public void setGt_min(String gt_min) {
		this.gt_min = gt_min;
	}

	@Pattern(regexp = "[1-9]\\d*|\\s?", message = "gt_max")
	public String getGt_max() {
		return gt_max.trim();
	}

	public void setGt_max(String gt_max) {
		this.gt_max = gt_max;
	}

	@Pattern(regexp = "[1-9]\\d*x[1-9]\\d*|\\s?", message = "gt_crop")
	public String getGt_crop() {
		return gt_crop.trim();
	}

	public void setGt_crop(String gt_crop) {
		this.gt_crop = gt_crop;
	}

	@Pattern(regexp = "webp|\\s?", message = "ex_fmt")
	public String getEx_fmt() {
		return ex_fmt;
	}

	public void setEx_fmt(String ex_fmt) {
		this.ex_fmt = ex_fmt;
	}

	public String getMutliq() {
		return mutliq;
	}

	public void setMutliq(String mutliq) {
		this.mutliq = mutliq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
