package com.melink.dialog.model;

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
