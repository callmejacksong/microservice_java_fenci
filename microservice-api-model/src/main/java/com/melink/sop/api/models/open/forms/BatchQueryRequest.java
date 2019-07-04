package com.melink.sop.api.models.open.forms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melink.sop.api.models.BaseViewForm;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchQueryRequest extends BaseViewForm {
	private static final long serialVersionUID = -7334902799003518587L;

	private List<String> codes;

	private List<String> names;

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

}
