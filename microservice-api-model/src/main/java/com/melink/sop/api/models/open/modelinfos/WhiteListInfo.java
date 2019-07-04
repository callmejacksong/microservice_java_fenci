package com.melink.sop.api.models.open.modelinfos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.melink.sop.api.models.BaseModeInfo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WhiteListInfo extends BaseModeInfo {

    private String version;

    private List<WhiteListItemInfo> list;

    private boolean reset;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<WhiteListItemInfo> getList() {
        return list;
    }

    public void setList(List<WhiteListItemInfo> list) {
        this.list = list;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }
}
