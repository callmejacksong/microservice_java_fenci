package com.melink.open.api.model;

import java.io.Serializable;
import java.util.Date;

public class IpAccount implements Serializable {

	private Integer id;

	private String name;

	private String icon;

	private String description;

	private Boolean verification;

	private Integer displayOrder;

	private Date createtime;

	private Date updatetime;

	private String innerLink;

	private String outerLink;

	private String banner;

	private String weibo;

	private String officialAccounts;

	private String video;

	private String baike;

	private String doubanMovie;

	private String doubanPage;

	private String miniBanner;

	private boolean ipActivity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getVerification() {
		return verification;
	}

	public void setVerification(Boolean verification) {
		this.verification = verification;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getInnerLink() {
		return innerLink;
	}

	public void setInnerLink(String innerLink) {
		this.innerLink = innerLink;
	}

	public String getOuterLink() {
		return outerLink;
	}

	public void setOuterLink(String outerLink) {
		this.outerLink = outerLink;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getOfficialAccounts() {
		return officialAccounts;
	}

	public void setOfficialAccounts(String officialAccounts) {
		this.officialAccounts = officialAccounts;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getBaike() {
		return baike;
	}

	public void setBaike(String baike) {
		this.baike = baike;
	}

	public String getDoubanMovie() {
		return doubanMovie;
	}

	public void setDoubanMovie(String doubanMovie) {
		this.doubanMovie = doubanMovie;
	}

	public String getDoubanPage() {
		return doubanPage;
	}

	public void setDoubanPage(String doubanPage) {
		this.doubanPage = doubanPage;
	}

	public String getMiniBanner() {
		return miniBanner;
	}

	public void setMiniBanner(String miniBanner) {
		this.miniBanner = miniBanner;
	}

	public boolean getIpActivity() {
		return ipActivity;
	}

	public void setIpActivity(boolean ipActivity) {
		this.ipActivity = ipActivity;
	}
}