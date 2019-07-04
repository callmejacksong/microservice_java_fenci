package com.melink.open.api.util;

import com.melink.microservice.json.JsonSerializer;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiPagination;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ Author     ：liyang.
 * @ Date       ：Created in 14:01 2019/1/22
 * @ Description：
 * @ Modified By：
 */
@Component
public class ResponseUtil<T> {

    public OpenApiV2ListResponse<T> successOpenApiListResponse(List<T> dataList, OpenApiPagination pagination) {
        OpenApiV2ListResponse<T> openApiResponseObject = new OpenApiV2ListResponse<T>(ErrorCode._SUCCESS, dataList, pagination);
        return openApiResponseObject;
    }

    public OpenApiPagination generatePagination(int total, int count, int page, int size) {
        OpenApiPagination pagination = new OpenApiPagination();
        pagination.setTotalCount(total);
        pagination.setCount(count);
        page--;
        int offset = page * size;
        pagination.setOffset(offset);
        return pagination;
    }

    public String errorResponseToStr(Integer errorCode, String message) {
        OpenApiV2ListResponse<String> stringApiResponseObject = new OpenApiV2ListResponse<String>(errorCode, message);
        JsonSerializer jsonSerializer = JsonSerializer.newInstance();
        String s = jsonSerializer.toJson(stringApiResponseObject);
        return s;
    }

}

