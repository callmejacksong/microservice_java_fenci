package com.melink.sop.api.models.open.modelinfos;

public class ImageInfo {

    private BasicNetPictureImage original;
    private BasicNetPictureImage large;
    private BasicNetPictureImage medium;
    private BasicNetPictureImage small;

    public BasicNetPictureImage getOriginal() {
        return original;
    }

    public void setOriginal(BasicNetPictureImage original) {
        this.original = original;
    }

    public BasicNetPictureImage getLarge() {
        return large;
    }

    public void setLarge(BasicNetPictureImage large) {
        this.large = large;
    }

    public BasicNetPictureImage getMedium() {
        return medium;
    }

    public void setMedium(BasicNetPictureImage medium) {
        this.medium = medium;
    }

    public BasicNetPictureImage getSmall() {
        return small;
    }

    public void setSmall(BasicNetPictureImage small) {
        this.small = small;
    }
}
