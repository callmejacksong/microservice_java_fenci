package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;

public class NetKeywordWeight implements Serializable {
    private String guid;
    private String keywordId;
    private int channe;
    private int scale;
    private int search;
    private int initial;
    private int weight;
    private Date updateTime;
    private int theme;
    private int chat;
    private int common;
    private int alipay;
    private int navi;
    private int category;
    private int hot;
    private int hotgiftag;
    private String background;

    private NetKeyword keyword;

    public NetKeywordWeight() {
        this.guid = GUIDGenerator.generate();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(String keywordId) {
        this.keywordId = keywordId;
    }

    public int getChanne() {
        return channe;
    }

    public void setChanne(int channe) {
        this.channe = channe;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getSearch() {
        return search;
    }

    public void setSearch(int search) {
        this.search = search;
    }

    public int getInitial() {
        return initial;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public int getChat() {
        return chat;
    }

    public void setChat(int chat) {
        this.chat = chat;
    }

    public int getCommon() {
        return common;
    }

    public void setCommon(int common) {
        this.common = common;
    }

    public NetKeyword getKeyword() {
        return keyword;
    }

    public void setKeyword(NetKeyword keyword) {
        this.keyword = keyword;
    }

    public int getAlipay() {
        return alipay;
    }

    public void setAlipay(int alipay) {
        this.alipay = alipay;
    }

    public int getNavi() {
        return navi;
    }

    public void setNavi(int navi) {
        this.navi = navi;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public int getHotgiftag() {
        return hotgiftag;
    }

    public void setHotgiftag(int hotgiftag) {
        this.hotgiftag = hotgiftag;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
