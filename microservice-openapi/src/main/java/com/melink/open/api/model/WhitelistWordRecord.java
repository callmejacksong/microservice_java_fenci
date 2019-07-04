package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;

//@Entity
//@Table(name = "whitelist_word_record")
public class WhitelistWordRecord implements Serializable {
	private String guid;

	private String text;

	private Integer type;

	private Integer version;

	private Date createtime;

	public WhitelistWordRecord() {
		this.guid = GUIDGenerator.generate();
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
