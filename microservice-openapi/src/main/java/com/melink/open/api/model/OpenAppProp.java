package com.melink.open.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.melink.microservice.utils.GUIDGenerator;

import java.io.Serializable;


/**
 * The persistent class for the open_app_prop database table.
 */
public class OpenAppProp implements Serializable {
    private String guid;

    private String name;

    private String value;

    private String openAppId;

    @JsonIgnore
    private OpenApp openApp;

    public OpenAppProp() {
        this.guid = GUIDGenerator.generate();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOpenAppId() {
        return openAppId;
    }

    public void setOpenAppId(String openAppId) {
        this.openAppId = openAppId;
    }

    public OpenApp getOpenApp() {
        return openApp;
    }

    public void setOpenApp(OpenApp openApp) {
        this.openApp = openApp;
    }

}