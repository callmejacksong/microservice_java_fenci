package com.melink.advert.service.impl;

import com.melink.advert.mapper.netPic.GlobalConfigMapper;
import com.melink.advert.service.GlobalConfigService;
import com.melink.microservice.cache.PlatformCache;
import com.melink.sop.api.models.RedisPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalConfigServiceImpl implements GlobalConfigService {
    @Autowired
    private GlobalConfigMapper globalConfigMapper;
    @Autowired
    private PlatformCache<String, Object> platformCache;

    @Override
    public Integer getGlobalSwitch() {
        Integer g = null;
        Object o = platformCache.get(RedisPrefix._ADVERT_GLOBAL_SWITCH, String.class);

        if (o != null) {
            g = Integer.parseInt(o.toString());

        } else {
            g = globalConfigMapper.findAdvertGlobal();
            platformCache.put(RedisPrefix._ADVERT_GLOBAL_SWITCH, g);
        }
        return g;
    }
}
