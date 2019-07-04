package com.melink.open.api.model;

import java.io.Serializable;
import java.util.Date;

public class BasicNetPictureLossy implements Serializable {
    private String np_id;

    private Integer type;

    private Integer fsize;

    private String md5;

    private Date createtime;

    private String lossyUrl;


    public BasicNetPictureLossy() {

    }

    private BasicNetPicture basicNetPicture;

    public BasicNetPictureLossy(String np_id, Integer type, Integer fsize, String md5, Date createtime, String lossyUrl) {
        this.np_id = np_id;
        this.type = type;
        this.fsize = fsize;
        this.md5 = md5;
        this.createtime = createtime;
        this.lossyUrl = lossyUrl;
    }

    public String getNp_id() {
        return np_id;
    }

    public void setNp_id(String np_id) {
        this.np_id = np_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFsize() {
        return fsize;
    }

    public void setFsize(Integer fsize) {
        this.fsize = fsize;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getLossyUrl() {
        return lossyUrl;
    }

    public void setLossyUrl(String lossyUrl) {
        this.lossyUrl = lossyUrl;
    }

    public BasicNetPicture getBasicNetPicture() {
        return basicNetPicture;
    }

    public void setBasicNetPicture(BasicNetPicture basicNetPicture) {
        this.basicNetPicture = basicNetPicture;
    }

}