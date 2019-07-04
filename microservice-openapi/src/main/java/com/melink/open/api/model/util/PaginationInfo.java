package com.melink.open.api.model.util;

import java.util.List;

public class PaginationInfo<T> {

	private Integer total;
	private List<T> dataList;
	private Integer filterd;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public Integer getFilterd() {
		return filterd;
	}
	public void setFilterd(Integer filterd) {
		this.filterd = filterd;
	}	
}
