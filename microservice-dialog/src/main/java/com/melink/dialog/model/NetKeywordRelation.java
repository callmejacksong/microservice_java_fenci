package com.melink.dialog.model;

import java.io.Serializable;

public class NetKeywordRelation implements Serializable {

    private String keyword_id;

    private String relation_id;

    private Integer seq;

    private String text;

    public NetKeywordRelation() {
    }

    public NetKeywordRelation(String text, Integer seq) {
        this.text = text;
        this.seq = seq;
    }

    public NetKeywordRelation(String keyword_id, String relation_id, Integer seq) {
        this.keyword_id = keyword_id;
        this.relation_id = relation_id;
        this.seq = seq;
    }

    private NetKeyword netKeyword;

    private NetKeyword relationNetKeyword;

    public String getKeyword_id() {
        return keyword_id;
    }

    public void setKeyword_id(String keyword_id) {
        this.keyword_id = keyword_id;
    }

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public NetKeyword getNetKeyword() {
        return netKeyword;
    }

    public void setNetKeyword(NetKeyword netKeyword) {
        this.netKeyword = netKeyword;
    }

    public NetKeyword getRelationNetKeyword() {
        return relationNetKeyword;
    }

    public void setRelationNetKeyword(NetKeyword relationNetKeyword) {
        this.relationNetKeyword = relationNetKeyword;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
