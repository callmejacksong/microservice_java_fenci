package com.melink.open.api.controller;

import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/error**")
@Controller
public class ErrorController extends AbstractDataController<String> {

    @RequestMapping("/invalid_param/{param}")
    @ResponseBody
    public OpenApiV2DataResponse<String> invalidParam(@PathVariable(value = "param") final String paramName) {
        return executeApiCall(() -> {
            String data = paramName + " is not valid";
            return errorResponse(ErrorCode._INVALID_PARAMETER, data);
        });

    }

    @RequestMapping("/bad_request/{param}")
    @ResponseBody
    public OpenApiV2DataResponse<String> badRequest(@PathVariable(value = "param") final String paramName) {
        return executeApiCall(() -> {
            String data = "illegal value of \"" + paramName + "\", can not perform the request.";
            return errorResponse(ErrorCode._BAD_REQUEST, data);
        });
    }

    @RequestMapping("/expired_token")
    @ResponseBody
    public OpenApiV2DataResponse<String> expiredToken() {
        return executeApiCall(() -> {
            String data = "The token is expired or not valid any more.";
            return errorResponse(ErrorCode._INVALID_ACCESS_TOKEN, data);
        });
    }
}
