package com.melink.open.api.model;

import java.io.Serializable;
import java.util.Date;

public class BasicNetPictureQuality implements Serializable {

    private String npId;

    private Integer quality;

    private String storeUrl;

    private Integer width;

    private Integer height;

    private Integer fsize;

    private String md5;

    private String name;

    private Integer weight;

    private Date createtime;

    //以下临时字段
    private String lossyUrl;
    private Integer lossyFsize;
    private String lossyMd5;

    private String thumb;

    private String webp;

    private Integer webpsize;

    private String webpmd5;

    private Integer classify;

    private Integer level;

    private Integer kind;

    private String keywords;

    private Integer scale;


    public BasicNetPictureQuality() {
    }

    private BasicNetPicture basicNetPicture;

    public BasicNetPictureQuality(String npId, String storeUrl, Integer width, Integer height, Integer fsize, String md5, Integer weight, String name, String lossyUrl, Integer lossyFsize, String lossyMd5, String thumb) {
        super();
        this.npId = npId;
        this.storeUrl = storeUrl;
        this.width = width;
        this.height = height;
        this.fsize = fsize;
        this.md5 = md5;
        this.weight = weight;
        this.name = name;
        this.lossyUrl = lossyUrl;
        this.lossyFsize = lossyFsize;
        this.lossyMd5 = lossyMd5;
        this.thumb = thumb;
    }

    public BasicNetPictureQuality(String npId, String storeUrl, Integer width, Integer height, Integer fsize, String md5, Integer weight, String name, String lossyUrl, Integer lossyFsize, String lossyMd5, String thumb, String webp, Integer webpsize, String webpmd5, Integer classify, Integer level, Integer kind) {
        super();
        this.npId = npId;
        this.storeUrl = storeUrl;
        this.width = width;
        this.height = height;
        this.fsize = fsize;
        this.md5 = md5;
        this.weight = weight;
        this.name = name;
        this.lossyUrl = lossyUrl;
        this.lossyFsize = lossyFsize;
        this.lossyMd5 = lossyMd5;
        this.thumb = thumb;
        this.setWebp(webp);
        this.setWebpsize(webpsize);
        this.setWebpmd5(webpmd5);
        this.classify = classify;
        this.level = level;
        this.kind = kind;
    }

    public BasicNetPictureQuality(String npId, String storeUrl, Integer width, Integer height, Integer fsize, String md5, Integer weight, String name, String lossyUrl, Integer lossyFsize, String lossyMd5, String thumb, String webp, Integer webpsize, String webpmd5, Integer classify, Integer level, Integer kind, String keywords) {
        super();
        this.npId = npId;
        this.storeUrl = storeUrl;
        this.width = width;
        this.height = height;
        this.fsize = fsize;
        this.md5 = md5;
        this.weight = weight;
        this.name = name;
        this.lossyUrl = lossyUrl;
        this.lossyFsize = lossyFsize;
        this.lossyMd5 = lossyMd5;
        this.thumb = thumb;
        this.setWebp(webp);
        this.setWebpsize(webpsize);
        this.setWebpmd5(webpmd5);
        this.classify = classify;
        this.level = level;
        this.kind = kind;
        this.keywords = keywords;
    }

    public BasicNetPictureQuality(String npId, String storeUrl, Integer width, Integer height, Integer fsize, String md5, Integer weight, String name, String lossyUrl, Integer lossyFsize, String lossyMd5, String thumb, String webp, Integer webpsize, String webpmd5, Integer classify, Integer level, Integer kind, Integer scale, String keywords) {
        super();
        this.npId = npId;
        this.storeUrl = storeUrl;
        this.width = width;
        this.height = height;
        this.fsize = fsize;
        this.md5 = md5;
        this.weight = weight;
        this.name = name;
        this.lossyUrl = lossyUrl;
        this.lossyFsize = lossyFsize;
        this.lossyMd5 = lossyMd5;
        this.thumb = thumb;
        this.setWebp(webp);
        this.setWebpsize(webpsize);
        this.setWebpmd5(webpmd5);
        this.classify = classify;
        this.level = level;
        this.kind = kind;
        this.scale = scale;
        this.keywords = keywords;
    }

    public String getnpId() {
        return npId;
    }

    public void setnpId(String npId) {
        this.npId = npId;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
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

    public Integer getFsize() {
        return fsize;
    }

    public void setFsize(Integer fsize) {
        this.fsize = fsize;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public BasicNetPicture getBasicNetPicture() {
        return basicNetPicture;
    }

    public void setBasicNetPicture(BasicNetPicture basicNetPicture) {
        this.basicNetPicture = basicNetPicture;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLossyUrl() {
        return lossyUrl;
    }

    public void setLossyUrl(String lossyUrl) {
        this.lossyUrl = lossyUrl;
    }

    public Integer getLossyFsize() {
        return lossyFsize;
    }

    public void setLossyFsize(Integer lossyFsize) {
        this.lossyFsize = lossyFsize;
    }

    public String getLossyMd5() {
        return lossyMd5;
    }

    public void setLossyMd5(String lossyMd5) {
        this.lossyMd5 = lossyMd5;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
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

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicNetPictureQuality that = (BasicNetPictureQuality) o;

        return npId != null ? npId.equals(that.npId) : that.npId == null;
    }

    @Override
    public int hashCode() {
        return npId.hashCode();
    }
}