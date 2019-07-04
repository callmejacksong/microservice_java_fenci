package com.melink.open.api.service.impl;

import com.melink.microservice.cache.PlatformCache;
import com.melink.open.api.constant.AppConsts;
import com.melink.open.api.mapper.melink.OpenAppMapper;
import com.melink.open.api.model.OpenApp;
import com.melink.open.api.service.OpenAppService;
import com.melink.sop.api.models.RedisPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAppServiceImpl implements OpenAppService {

    @Autowired
    private PlatformCache<String, Object> platformCache;
    @Autowired
    protected OpenAppMapper openAppMapper;

    private int tokenExpiresIn = 60 * 60 * 2;
    private static final String _TOKEN_TAG = "ac-";
    private static final String _APP_AC_TAG = "app-ac-";

    public List<OpenApp> findAppsByPlatformId(String platformId, int size) {
        return openAppMapper.findAppsByPlatformId(platformId, size);
    }

    public OpenApp getAppByCache(String appId) {
        OpenApp app = (OpenApp) platformCache.getObject(RedisPrefix._OPEN_APP_TAG + appId);
        if (app == null) {
            app = openAppMapper.findByGuid(appId);
            if (app != null)
                platformCache.putByte(RedisPrefix._OPEN_APP_TAG + appId, app, tokenExpiresIn);
        }
        return app;
    }

    //BaiduEmoticionServiceImpl调用
    public boolean isTestApp(String appId) {
        OpenApp app = getAppByCache(appId);
        if (app == null) {
            return false;
        }
        if (AppConsts.TEST_TYPE.equals(app.getType())) {
            return true;
        }
        return false;
    }



}
