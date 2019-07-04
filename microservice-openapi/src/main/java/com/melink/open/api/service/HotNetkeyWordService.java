package com.melink.open.api.service;

import com.melink.sop.api.models.open.modelinfos.HotNetKeywordInfo;

import java.util.List;

public interface HotNetkeyWordService {
    List<HotNetKeywordInfo> hot(String timestamp, String signature, String appId, boolean sslRes, boolean mutliEmo);
}
