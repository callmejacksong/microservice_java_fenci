package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
	public class OpenEmoticion extends BaseModeInfo {

	private static final long serialVersionUID = -1167859636624629585L;
	private String guid;
	private String text;
	private String code;
	private String thumb;
	private String gif_thumb;
	private String main;
	@JsonProperty(value = "package_id")
	private String packageId;
	private Integer width;
	private Integer height;
	@JsonProperty(value = "is_animated")
	private String isAnimated;
	private String copyright;
	@JsonProperty(value = "fsize")
	private Integer fs;
	private String md5;
	private List<String> fenciWords;
	@JsonIgnore
	private boolean sslRes;
//    @JsonIgnore
    private int weight;
	@JsonIgnore
	private int keyWordWeight;
	@JsonIgnore
	private int trendLock;
	@JsonIgnore
	private String hitKeyword;

	private String webp;
	private Integer webpsize;
	private String webpmd5;
	@JsonIgnore
	private List<String> keywords;
//	@JsonIgnore
	private List<String> allKeywords;
	//	@JsonIgnore
	private List<String> relationWords;

	private int classify;
	@JsonIgnore
	private int level;
	@JsonIgnore
	private int kind;

	//广告图片的位置
	private Integer advertPosition;

	//是否是广告图片
	@JsonProperty(value = "is_tmp_adgif")
	private Boolean isAdvert = null;
	//广告信息
	private EmoticionPromotion promotion;

	public Boolean getIsAdvert() {
		return this.isAdvert;
	}

	public void setIsAdvert(Boolean advert) {
		this.isAdvert = advert;
	}
	public void setFenciWords(List<String> fenciWords) {
		this.fenciWords = fenciWords;
	}

	public Integer getAdvertPosition() {
		return advertPosition;
	}
	public List<String> getFenciWords() {
		return fenciWords;
	}

	public void setAdvertPosition(Integer advertPosition) {
		this.advertPosition = advertPosition;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getIsAnimated() {
		return isAnimated;
	}

	public void setIsAnimated(String isAnimated) {
		this.isAnimated = isAnimated;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public boolean isSslRes() {
		return sslRes;
	}

	public void setSslRes(boolean sslRes) {
		this.sslRes = sslRes;
	}

	public Integer getFs() {
		return fs;
	}

	public void setFs(Integer fs) {
		this.fs = fs;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		OpenEmoticion emoticion = (OpenEmoticion) obj;
        return main.equals(emoticion.main);
	}

	public String getGif_thumb() {
		return gif_thumb;
	}

	public void setGif_thumb(String gif_thumb) {
		this.gif_thumb = gif_thumb;
	}

	@Override
    public int hashCode() {
        return main.hashCode();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

	public int getKeyWordWeight() {
		return keyWordWeight;
	}

	public void setKeyWordWeight(int keyWordWeight) {
		this.keyWordWeight = keyWordWeight;
	}

	public int getTrendLock() {
		return trendLock;
	}

	public void setTrendLock(int trendLock) {
		this.trendLock = trendLock;
	}

	public String getHitKeyword() {
		return hitKeyword;
	}

	public void setHitKeyword(String hitKeyword) {
		this.hitKeyword = hitKeyword;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public String getWebp() {
		return webp;
	}

	public void setWebp(String webp) {
		this.webp = webp;
	}

	public Integer getWebpsize() {
		return webpsize;
	}

	public void setWebpsize(Integer webpsize) {
		this.webpsize = webpsize;
	}

	public String getWebpmd5() {
		return webpmd5;
	}

	public void setWebpmd5(String webpmd5) {
		this.webpmd5 = webpmd5;
	}

	public int getClassify() {
		return classify;
	}

	public void setClassify(int classify) {
		this.classify = classify;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public List<String> getAllKeywords() {
		return allKeywords;
	}
	public List<String> getRelationWords() {
		return relationWords;
	}

	public void setAllKeywords(List<String> allKeywords) {
		this.allKeywords = allKeywords;
	}
	public void setRelationWords(List<String> relationWords) {
		this.relationWords = relationWords;
	}

	public EmoticionPromotion getPromotion() {
		return promotion;
	}

	public void setPromotion(EmoticionPromotion promotion) {
		this.promotion = promotion;
	}
}