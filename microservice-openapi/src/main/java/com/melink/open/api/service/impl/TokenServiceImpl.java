package com.melink.open.api.service.impl;

import com.melink.microservice.cache.PlatformCache;
import com.melink.microservice.utils.GUIDGenerator;
import com.melink.open.api.service.TokenService;
import com.melink.sop.api.models.RedisPrefix;
import com.melink.sop.api.models.open.modelinfos.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private PlatformCache<String, Object> platformCache;

    @Override
    public AccessToken refreshToken(String appId) {
        String token = GUIDGenerator.generate();
        int ttl = 60 * 60 * 6;
        platformCache.put(RedisPrefix._OPEN_JSSDK_TOKEN + appId, token, ttl);
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(token);
        accessToken.setExpiresIn(ttl);
        return accessToken;
    }
}
