package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendyEmoticonInfo extends BaseModeInfo {
	private static final long serialVersionUID = 2594676813263484812L;
	//探探流行表情总数
	private long tt_count;
	private String tt_main;
	private Date tt_updatedtime;

	public long getTt_count() {
		return tt_count;
	}

	public void setTt_count(long tt_count) {
		this.tt_count = tt_count;
	}

	public String getTt_main() {
		return tt_main;
	}

	public void setTt_main(String tt_main) {
		this.tt_main = tt_main;
	}

	public Date getTt_updatedtime() {
		return tt_updatedtime;
	}

	public void setTt_updatedtime(Date tt_updatedtime) {
		this.tt_updatedtime = tt_updatedtime;
	}
}