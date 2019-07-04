package com.melink.sop.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenApiResponseObject<T> extends ApiResponseObject<T> {

	private static final long serialVersionUID = -1L;

	private List<T> categories;

	private List<T> packages;

	@JsonProperty(value = "category_packages")
	private T categoryPackages;

	private List<T> emojis;

	private List<T> stickers;

	private List<T> gifs;

	private T emoji;

	@JsonProperty(value = "package")
	private T categoryPackage;

	@JsonProperty(value = "hot_keywords")
	private List<T> hotKeywords;

	private Integer count;

	@JsonProperty(value = "keywords")
	private List<String> keywords;


	public OpenApiResponseObject() {

	}

	public OpenApiResponseObject(Integer errorCode, String msg) {
		super(errorCode, msg);
	}

	public OpenApiResponseObject(Integer errorCode, String msg, T data) {
		super(errorCode, msg, data);
	}

	public OpenApiResponseObject(Integer errorCode) {
		super(errorCode);
	}

	public OpenApiResponseObject(Integer errorCode, String msg, List<T> dataList) {
		super(errorCode, msg, dataList);
	}

	public List<T> getCategories() {
		return categories;
	}

	public void setCategories(List<T> categories) {
		this.categories = categories;
	}

	public List<T> getPackages() {
		return packages;
	}

	public void setPackages(List<T> packages) {
		this.packages = packages;
	}

	public T getCategoryPackages() {
		return categoryPackages;
	}

	public void setCategoryPackages(T categoryPackages) {
		this.categoryPackages = categoryPackages;
	}

	public List<T> getEmojis() {
		return emojis;
	}

	public void setEmojis(List<T> emojis) {
		this.emojis = emojis;
	}

	public T getEmoji() {
		return emoji;
	}

	public void setEmoji(T emoji) {
		this.emoji = emoji;
	}

	public T getCategoryPackage() {
		return categoryPackage;
	}

	public void setCategoryPackage(T categoryPackage) {
		this.categoryPackage = categoryPackage;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<T> getHotKeywords() {
		return hotKeywords;
	}

	public void setHotKeywords(List<T> hotKeywords) {
		this.hotKeywords = hotKeywords;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<T> getGifs() {
		return gifs;
	}

	public void setGifs(List<T> gifs) {
		this.gifs = gifs;
	}

	public List<T> getStickers() {
		return stickers;
	}

	public void setStickers(List<T> stickers) {
		this.stickers = stickers;
	}

}