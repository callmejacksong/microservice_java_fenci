package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;

public class BasicNetPicturePv implements Serializable {

	private String guid;

	private Integer pv;

	private Integer mobilePv;

	public BasicNetPicturePv() {
		this.guid = GUIDGenerator.generate();
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public Integer getMobilePv() {
		return mobilePv;
	}

	public void setMobilePv(Integer mobilePv) {
		this.mobilePv = mobilePv;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

}

