package com.melink.sop.api.models.open.forms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.melink.sop.api.models.BaseViewForm;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPlusEventItemRequest extends BaseViewForm {

    private static final long serialVersionUID = -1394147417667786105L;

    @JsonProperty("event_title")
    private String eventTitle;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("event_params")
    private Map<String,String> eventParams;

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map<String, String> getEventParams() {
        return eventParams;
    }

    public void setEventParams(Map<String, String> eventParams) {
        this.eventParams = eventParams;
    }
}
