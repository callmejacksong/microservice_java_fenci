package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotNetKeywordInfo extends BaseModeInfo {

    private static final long serialVersionUID = 8823421038496607053L;
    private String text;
    private String cover;
    private Integer width;
    private Integer height;
    private Integer fs;
    private List<OpenEmoticion> emoticions;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;

    }
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public Integer getFs() {
        return fs;
    }

    public void setFs(Integer fs) {
        this.fs = fs;
    }

    public List<OpenEmoticion> getEmoticions() {
        return emoticions;
    }

    public void setEmoticions(List<OpenEmoticion> emoticions) {
        this.emoticions = emoticions;
    }
}
