package com.melink.sop.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @ Author     ：liyang.
 * @ Date       ：Created in 11:53 2019/1/22
 * @ Description：
 * @ Modified By：
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenApiPagination implements Serializable {
    private int count;

    @JsonProperty("total_count")
    private int totalCount;

    private int offset;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
