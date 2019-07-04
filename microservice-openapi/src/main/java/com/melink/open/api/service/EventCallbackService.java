package com.melink.open.api.service;

import com.melink.open.api.vo.ApiPlusVO;
import com.melink.sop.api.models.open.forms.ApiPlusEventRequest;

public interface EventCallbackService {

    void callback(ApiPlusEventRequest apiPlusEventRequest, ApiPlusVO apiPlusVO);

}
