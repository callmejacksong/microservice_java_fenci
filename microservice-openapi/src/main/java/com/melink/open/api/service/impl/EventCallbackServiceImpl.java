package com.melink.open.api.service.impl;

import com.melink.microservice.utils.DateUtils;
import com.melink.microservice.utils.PlatformUtils;
import com.melink.open.api.service.EventCallbackService;
import com.melink.open.api.vo.ApiPlusVO;
import com.melink.sop.api.models.open.forms.ApiPlusEventItemRequest;
import com.melink.sop.api.models.open.forms.ApiPlusEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EventCallbackServiceImpl implements EventCallbackService {
    private static Logger log = LoggerFactory.getLogger(EventCallbackServiceImpl.class);


    @Override
    public void callback(ApiPlusEventRequest apiPlusEventRequest, ApiPlusVO apiPlusVO) {
        for (ApiPlusEventItemRequest apiPlusEventItemRequest : apiPlusEventRequest.getEventList()) {
            String time = DateUtils.getDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            String eventLogFormat = "%s__%s__%s__%s__%s__%s__%s__%s__%s";

            String paramStr = PlatformUtils.buildQueryString(apiPlusEventItemRequest.getEventParams(), true);

            String package_name;

            if((package_name = apiPlusVO.getBundle_id()) == null){
                package_name = apiPlusVO.getPackage_name();
            }

            String eventLog = String.format(eventLogFormat, time, apiPlusVO.getApp_id(), apiPlusVO.getOpenid(),apiPlusVO.getApp_name(),package_name, apiPlusVO.getOs(), apiPlusVO.getSdk_version(), apiPlusEventItemRequest.getEventTitle(), paramStr);
            log.error(eventLog);
        }
    }
}