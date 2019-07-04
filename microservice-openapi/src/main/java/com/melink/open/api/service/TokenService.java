package com.melink.open.api.service;

import com.melink.sop.api.models.open.modelinfos.AccessToken;

public interface TokenService {
    AccessToken refreshToken(String appId);
}
