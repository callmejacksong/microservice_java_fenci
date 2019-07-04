package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NetKeywordRelationInfo extends BaseModeInfo {

	private static final long serialVersionUID = -5018037800755940481L;
	private String text;
	private Integer type;
	private Integer relationWeight;
	private List<NetKeywordRelationInfo> relationWords;

    private List<OpenEmoticion> openEmoticions;

	private HashMap<Integer,List<OpenEmoticion>> categoryMap;

	public static final Integer C1 = 1;	//分类类型
	public static final Integer C2 = 2;	//关键词类型
	public static final Integer C3 = 4;	//文案类型
	public static final Integer C4 = 0; //不属于词库表,用户搜索

	public NetKeywordRelationInfo(String text, Integer type) {
		this.text = text;
		this.type = type;
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

	public Integer getRelationWeight() {
		return relationWeight;
	}

	public void setRelationWeight(Integer relationWeight) {
		this.relationWeight = relationWeight;
	}

	public List<NetKeywordRelationInfo> getRelationWords() {
		return relationWords;
	}

	public void setRelationWords(List<NetKeywordRelationInfo> relationWords) {
		this.relationWords = relationWords;
	}

	public List<OpenEmoticion> getOpenEmoticions() {
		return openEmoticions;
	}

	public void setOpenEmoticions(List<OpenEmoticion> openEmoticions) {
		this.openEmoticions = openEmoticions;
	}

	public HashMap<Integer, List<OpenEmoticion>> getCategoryMap() {
		return categoryMap;
	}

	public void setCategoryMap(HashMap<Integer, List<OpenEmoticion>> categoryMap) {
		this.categoryMap = categoryMap;
	}
}
