package com.melink.sop.completion.search.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.melink.sop.completion.search.common.RestCodeEnum.SUCCESS;


/**
 * <p>rest api 结果</p>
 *
 * @author: XianGuo
 * @date: 2018年02月10日
 */
@Data
public class RestResult<T> {

    private int status;

    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public void setRestCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public void setRestCodeEnum(RestCodeEnum restCodeEnum) {
        this.setRestCode(restCodeEnum.getCode(), restCodeEnum.getDesc());
    }

    public static <T> RestResult<T> getSuccessResult(T data) {
        RestResult<T> restResult = new RestResult<>();
        restResult.setRestCodeEnum(SUCCESS);
        restResult.setData(data);
        return restResult;
    }

    public static RestResult getFailResult(int status, String msg) {
        RestResult restResult = new RestResult();
        restResult.setRestCode(status, msg);
        return restResult;
    }

    public static RestResult getFailResult(RestCodeEnum restCodeEnum) {
        return getFailResult(restCodeEnum.getCode(), restCodeEnum.getDesc());
    }

}
