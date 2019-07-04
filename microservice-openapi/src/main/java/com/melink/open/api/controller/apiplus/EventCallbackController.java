package com.melink.open.api.controller.apiplus;

import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.controller.AbstractDataController;
import com.melink.open.api.service.EventCallbackService;
import com.melink.open.api.vo.ApiPlusVO;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.open.forms.ApiPlusEventRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/event")
@RestController
public class EventCallbackController extends AbstractDataController<String> {
    @Autowired
    private EventCallbackService eventCallbackService;

    @RequestMapping("callback")
    @NewRequiredAuth
    public OpenApiV2DataResponse<String> callback(@RequestBody final ApiPlusEventRequest apiPlusEventRequest, final ApiPlusVO apiPlusVO) {
        return executeApiCall(new ApiCallback<String>() {
            @Override
            public OpenApiV2DataResponse<String> call() {
                if (CollectionUtils.isEmpty(apiPlusEventRequest.getEventList())) {
                    return successResponse("success");
                }
                eventCallbackService.callback(apiPlusEventRequest, apiPlusVO);

                return successResponse("success");
            }
        });
    }
}