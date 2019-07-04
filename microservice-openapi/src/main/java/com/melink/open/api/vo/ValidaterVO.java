package com.melink.open.api.vo;

import javax.validation.constraints.Pattern;

public class ValidaterVO {
	private String p = "1";
	private String size = "20";
	private String partner = "";

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

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

}
