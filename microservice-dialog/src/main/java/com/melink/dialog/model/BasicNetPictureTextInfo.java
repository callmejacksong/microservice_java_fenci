package com.melink.dialog.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;

public class BasicNetPictureTextInfo implements Serializable {

	private String guid;
	private String textinfo;
	private Integer ak;

	private BasicNetPicture basicNetPicture;

	public BasicNetPictureTextInfo() {
		this.guid = GUIDGenerator.generate();
	}

	public String getTextinfo() {
		return textinfo;
	}

	public void setTextinfo(String textinfo) {
		this.textinfo = textinfo;
	}

	public Integer getAk() {
		return ak;
	}

	public void setAk(Integer ak) {
		this.ak = ak;
	}

	public BasicNetPicture getBasicNetPicture() {
		return basicNetPicture;
	}

	public void setBasicNetPicture(BasicNetPicture basicNetPicture) {
		this.basicNetPicture = basicNetPicture;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

}
