package com.melink.sop.api.models.open.modelinfos;

import java.io.Serializable;

public class BasicNetPictureImage implements Serializable {

    private Integer width;
    private Integer height;
    private String url;
    private Integer fsize;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFsize() {
        return fsize;
    }

    public void setFsize(Integer fsize) {
        this.fsize = fsize;
    }
}
