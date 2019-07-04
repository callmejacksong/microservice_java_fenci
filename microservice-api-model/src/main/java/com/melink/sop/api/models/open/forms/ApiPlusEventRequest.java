package com.melink.sop.api.models.open.forms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseViewForm;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPlusEventRequest extends BaseViewForm {

    private static final long serialVersionUID = -5209866906476195910L;


    private String openid;

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("event_list")
    private List<ApiPlusEventItemRequest> eventList;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<ApiPlusEventItemRequest> getEventList() {
        return eventList;
    }

    public void setEventList(List<ApiPlusEventItemRequest> eventList) {
        this.eventList = eventList;
    }
}
