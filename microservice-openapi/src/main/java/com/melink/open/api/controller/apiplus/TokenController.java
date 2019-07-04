package com.melink.open.api.controller.apiplus;

import com.melink.open.api.controller.AbstractDataController;
import com.melink.open.api.model.OpenApp;
import com.melink.open.api.service.OpenAppService;
import com.melink.open.api.service.TokenService;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.open.modelinfos.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RequestMapping("/jssdk")
@RestController
public class TokenController extends AbstractDataController<AccessToken> {

    @Autowired
    private OpenAppService openAppService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping("/token")
    public OpenApiV2DataResponse<AccessToken> refreshToken(@RequestParam(value = "app_id",required = false) final String appId,
                                                           @RequestParam(value = "app_secret",required = false) final String appSecret) {
        return executeApiCall(new ApiCallback<AccessToken>() {
            @Override
            public OpenApiV2DataResponse<AccessToken> call() {

                if(!StringUtils.hasText(appId)){
                    return errorResponse(ErrorCode._INVALID_PARAMETER,"app_id is not valid");
                }

                if(!StringUtils.hasText(appSecret)){
                    return errorResponse(ErrorCode._INVALID_PARAMETER,"app_secret is not valid");
                }

                OpenApp app = openAppService.getAppByCache(appId);
                if (app == null) {
                    return errorResponse(ErrorCode._INVALID_PARAMETER,"app_id is not valid");
                }
                if (!app.getIsactive()) {
                    return errorResponse(ErrorCode._INVALID_PARAMETER,"app is disabled");
                }
                if (app.getExpiretime() != null && app.getExpiretime().before(new Date())) {
                    return errorResponse(ErrorCode._INVALID_PARAMETER,"app is expired");
                }

                if(!app.getAppsecret().equals(appSecret)){
                    return errorResponse(ErrorCode._INVALID_PARAMETER,"app_secret mismatch");
                }
                AccessToken accessToken = tokenService.refreshToken(appId);
                return successResponse(accessToken);
            }
        });
    }

}