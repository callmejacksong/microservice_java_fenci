package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;

public class NetPictureKeyword implements Serializable {

    private String guid;

    private String npId;

    private String keywordId;

    private Integer operator;

    private Date createtime;

    private Integer status;

    private Integer weight;

    private NetKeyword keyword;

    private BasicNetPicture basicNetPicture;

    public NetPictureKeyword() {
        this.guid = GUIDGenerator.generate();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getNpId() {
        return npId;
    }

    public void setNpId(String npId) {
        this.npId = npId;
    }

    public String getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(String keywordId) {
        this.keywordId = keywordId;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public NetKeyword getKeyword() {
        return keyword;
    }

    public void setKeyword(NetKeyword keyword) {
        this.keyword = keyword;
    }

    public BasicNetPicture getBasicNetPicture() {
        return basicNetPicture;
    }

    public void setBasicNetPicture(BasicNetPicture basicNetPicture) {
        this.basicNetPicture = basicNetPicture;
    }

}
