package com.melink.open.api.service;

import com.melink.sop.api.models.open.modelinfos.WhiteListInfo;

public interface WhiteWordService {
    WhiteListInfo whitelistCheck(String version);

}
