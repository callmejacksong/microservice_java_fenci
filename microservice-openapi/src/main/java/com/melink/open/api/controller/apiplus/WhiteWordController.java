package com.melink.open.api.controller.apiplus;

import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.controller.AbstractDataController;
import com.melink.open.api.service.WhiteWordService;
import com.melink.open.api.vo.ApiPlusVO;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.open.modelinfos.WhiteListInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/whitelist")
@RestController
public class WhiteWordController extends AbstractDataController<WhiteListInfo> {

    @Autowired
    private WhiteWordService whiteWordService;

    @RequestMapping("/check")
    @NewRequiredAuth
    public OpenApiV2DataResponse<WhiteListInfo> whitelistCheck(@RequestParam(value = "version",required = false) final String version,
                                                           final ApiPlusVO apiPlusVO) {
        return executeApiCall(new ApiCallback<WhiteListInfo>() {
            @Override
            public OpenApiV2DataResponse<WhiteListInfo> call() {
                WhiteListInfo result = whiteWordService.whitelistCheck(version);
                return successResponse(result);
            }
        });
    }


}