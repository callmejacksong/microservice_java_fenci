package com.melink.sop.api.models.dialog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DialogImageInfo {

    private String url;
    private Integer type;
    @JsonProperty(value = "robot_req")
    private String robotReq;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRobotReq() {
        return robotReq;
    }

    public void setRobotReq(String robotReq) {
        this.robotReq = robotReq;
    }
}
