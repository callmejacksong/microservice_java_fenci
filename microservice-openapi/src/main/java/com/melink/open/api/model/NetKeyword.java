package com.melink.open.api.model;

import com.alibaba.ans.shaded.com.alibaba.fastjson.annotation.JSONField;
import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NetKeyword implements Serializable {

    private String guid;

    private String category;

    private String text;

    private Date createtime;

    private Integer weight;

    private Integer clevel;

    @JSONField(name = "is_core")
    private Integer isCore;

    private Integer maintain;

    private Integer kind;
    private List<String> fenci;


    private List<NetPictureKeyword> netPictureKeywords;

    private List<NetKeywordRelation> netKeywordRelations;

    public NetKeyword() {
        this.guid = GUIDGenerator.generate();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
    public void setIsCore(Integer isCore) {
        this.isCore = isCore;
    }
    public void setKind(Integer kind) {
        this.kind = kind;
    }
    public void setMaintain(Integer maintain) {
        this.maintain = maintain;
    }
    public void setFenci(List<String> fenci) {
        this.fenci = fenci;
    }
    public List<String> getFenci() {
        return fenci;
    }
    public Integer getIsCore() {
        return isCore;
    }
    public Integer getKind() {
        return kind;
    }
    public Integer getMaintain() {
        return maintain;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public List<NetPictureKeyword> getNetPictureKeywords() {
        return netPictureKeywords;
    }

    public void setNetPictureKeywords(List<NetPictureKeyword> netPictureKeywords) {
        this.netPictureKeywords = netPictureKeywords;
    }

    public List<NetKeywordRelation> getNetKeywordRelations() {
        return netKeywordRelations;
    }

    public void setNetKeywordRelations(List<NetKeywordRelation> netKeywordRelations) {
        this.netKeywordRelations = netKeywordRelations;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getClevel() {
        return clevel;
    }

    public void setClevel(Integer clevel) {
        this.clevel = clevel;
    }
}
