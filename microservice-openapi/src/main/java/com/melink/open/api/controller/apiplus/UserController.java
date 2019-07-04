package com.melink.open.api.controller.apiplus;

import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.controller.AbstractDataController;
import com.melink.open.api.service.UserService;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.open.modelinfos.OpenUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/user")
@RestController
public class UserController extends AbstractDataController<OpenUserInfo> {

    @Autowired
    private UserService userService;


    @RequestMapping("/auth")
    @NewRequiredAuth
    public OpenApiV2DataResponse<OpenUserInfo> auth(
            @RequestParam(value = "app_id") final String appId,
            @RequestParam(value = "device_no", required = false) final String deviceNo,
            @RequestParam(value = "name", required = false) final String name,
            @RequestParam(value = "gender", required = false, defaultValue = "0") final Integer gender,
            @RequestParam(value = "third_key", required = false) final String thirdKey,
            @RequestParam(value = "openid", required = false) final String openid,
            @RequestParam(value = "info", required = false) final String info

    ) {
        return executeApiCall(new ApiCallback<OpenUserInfo>() {
            @Override
            public OpenApiV2DataResponse<OpenUserInfo> call() {
                if (!StringUtils.hasText(deviceNo) && !StringUtils.hasText(thirdKey)) {
                    return errorResponse(ErrorCode._INVALID_PARAMETER, "Missing unique identifier");
                }
                OpenUserInfo openUserInfo = userService.auth(appId,deviceNo,name,gender,thirdKey,openid,info);
                if(openUserInfo == null){
                    return errorResponse(ErrorCode._INVALID_PARAMETER, "openid not found");
                }
                return successResponse(openUserInfo);
            }
        });
    }
}