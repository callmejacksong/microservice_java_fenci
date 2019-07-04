package com.melink.sop.api.models.open.modelinfos;

public class BasicNetPictureInfo {
    private String guid;

    private String text;
    private Integer isAnimate;
    private Integer classify;

    private String copyright;
    private String createTime;

    private ImageInfo image;

    public ImageInfo getImage() {
        return image;
    }

    public void setImage(ImageInfo image) {
        this.image = image;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIsAnimate() {
        return isAnimate;
    }

    public void setAnimate(int animate) {
        isAnimate = animate;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
