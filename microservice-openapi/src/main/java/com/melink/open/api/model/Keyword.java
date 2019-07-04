package com.melink.open.api.model;

import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the basic_emoticon_label database table.
 */
public class Keyword implements Serializable {
    private String guid;

    private String category;

    private String text;

    private Date createtime;

    private List<BasicEmoticon> emoji;

    private Boolean isHot;

    private Date updatetime;

    public Keyword() {
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

    public List<BasicEmoticon> getEmoji() {
        return emoji;
    }

    public void setEmoji(List<BasicEmoticon> emoji) {
        this.emoji = emoji;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}