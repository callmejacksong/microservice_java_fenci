package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BasicNetPicture implements Serializable {

    private String guid;

    private String netUrl;

    private String storeUrl;

    private String textinfo;
    private String text;

    private Integer width;

    private Integer height;

    private Integer dynamic;

    private String name;

    private String sfrom;

    private Integer fsize;

    private Integer wt;

    private Integer level;

    private Integer kind;

    private String createtime;

    private String copyright;

    private List<NetPictureKeyword> netPictureKeywords;

    private List<BasicNetPictureQuality> basicNetPictureQualities;

    private List<BasicNetPictureLossy> basicNetPictureLossys;

    private BasicNetPictureTextInfo netPictureTextInfo;

    private Long count;

    private Integer weight;

    private Integer classify;

    private String webp;

    private String dynamic_thumb;
    private List<String> fenciWords;
    private String thumb;

    public BasicNetPicture() {
        this.guid = GUIDGenerator.generate();
    }

    public BasicNetPicture(String netUrl, String storeUrl, Integer width, Integer height, Integer dynamic, String name, String sfrom, Integer fsize, Integer wt, Integer level, Integer weight, String createtime, Integer classify, Integer kind) {
        this.netUrl = netUrl;
        this.storeUrl = storeUrl;
        this.width = width;
        this.height = height;
        this.dynamic = dynamic;
        this.name = name;
        this.sfrom = sfrom;
        this.fsize = fsize;
        this.wt = wt;
        this.level = level;
        this.weight = weight;
        this.createtime = createtime;
        this.classify = classify;
        this.kind = kind;
    }

    public BasicNetPicture(String storeUrl, String webp, String dynamic_thumb) {
        this.storeUrl = storeUrl;
        this.webp = webp;
        this.dynamic_thumb = dynamic_thumb;
    }

    public String getNetUrl() {
        return netUrl;
    }
    public List<String> getFenciWords() {
        return fenciWords;
    }
    public void setFenciWords(List<String> fenciWords) {
        this.fenciWords = fenciWords;
    }

    public void setNetUrl(String netUrl) {
        this.netUrl = netUrl;
    }

    public String getStoreUrl() {
        return storeUrl;
    }
    public String getThumb() {
        return thumb;
    }
    public String getText() {
        return text;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    public void setText(String text) {
        this.text = text;
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

    public Integer getDynamic() {
        return dynamic;
    }

    public void setDynamic(Integer dynamic) {
        this.dynamic = dynamic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSfrom() {
        return sfrom;
    }

    public void setSfrom(String sfrom) {
        this.sfrom = sfrom;
    }
    public void setTextinfo(String textinfo) {
        this.textinfo = textinfo;
    }

    public String getCreatetime() {
        return createtime;
    }
    public String getTextinfo() {
        return textinfo;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public List<NetPictureKeyword> getNetPictureKeywords() {
        return netPictureKeywords;
    }

    public void setNetPictureKeywords(List<NetPictureKeyword> netPictureKeywords) {
        this.netPictureKeywords = netPictureKeywords;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        BasicNetPicture s = (BasicNetPicture) obj;
        return guid.equals(s.guid);
    }

    @Override
    public int hashCode() {
        return guid.hashCode();
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getFsize() {
        return fsize;
    }

    public void setFsize(Integer fsize) {
        this.fsize = fsize;
    }

    public List<BasicNetPictureQuality> getBasicNetPictureQualities() {
        return basicNetPictureQualities;
    }

    public void setBasicNetPictureQualities(List<BasicNetPictureQuality> basicNetPictureQualities) {
        this.basicNetPictureQualities = basicNetPictureQualities;
    }

    public Integer getWt() {
        return wt;
    }

    public void setWt(Integer wt) {
        this.wt = wt;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public BasicNetPictureTextInfo getNetPictureTextInfo() {
        return netPictureTextInfo;
    }

    public void setNetPictureTextInfo(BasicNetPictureTextInfo netPictureTextInfo) {
        this.netPictureTextInfo = netPictureTextInfo;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public String getWebp() {
        return webp;
    }

    public void setWebp(String webp) {
        this.webp = webp;
    }

    public String getDynamic_thumb() {
        return dynamic_thumb;
    }

    public void setDynamic_thumb(String dynamic_thumb) {
        this.dynamic_thumb = dynamic_thumb;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public List<BasicNetPictureLossy> getBasicNetPictureLossys() {
        return basicNetPictureLossys;
    }

    public void setBasicNetPictureLossys(List<BasicNetPictureLossy> basicNetPictureLossys) {
        this.basicNetPictureLossys = basicNetPictureLossys;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
