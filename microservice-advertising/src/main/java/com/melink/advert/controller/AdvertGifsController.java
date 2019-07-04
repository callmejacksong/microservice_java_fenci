package com.melink.advert.controller;

import com.melink.advert.service.AdvertService;
import com.melink.advert.service.GlobalConfigService;
import com.melink.microservice.cache.PlatformCache;
import com.melink.sop.api.constant.advertConstant.AdvertSwitchConstant;
import com.melink.sop.api.constant.advertConstant.AdvertTypeConstant;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class AdvertGifsController {

    @Autowired
    private AdvertService advertService;
    @Autowired
    private PlatformCache<String, Object> platformCache;
    @Autowired
    private GlobalConfigService globalConfigService;

    @GetMapping("obtain/{type}")
    public List<OpenEmoticion> getAdvertByType(@PathVariable("type") String type) {
        //获取全局开关
        Integer g = globalConfigService.getGlobalSwitch();
        Map<String, List<OpenEmoticion>> cache = null;
        //根据type 为 trending or search 判断开关获取数据
        if (AdvertTypeConstant.ADVERT_TYPE_TRENDING.equals(type)) {
            if ((g != null) && (g == AdvertSwitchConstant.ADVERT_OPEN_UP || g == AdvertSwitchConstant.ADVERT_OPEN_UP_TRENDING)) {
                cache = advertService.getAdvertByType(type);
            }
        } else if (AdvertTypeConstant.ADVERT_TYPE_SEARCH.equals(type)) {
            if (g != null && (g == AdvertSwitchConstant.ADVERT_OPEN_UP || g == AdvertSwitchConstant.ADVERT_OPEN_UP_SEARCH)) {
                cache = advertService.getAdvertByType(type);
            }
        }
        //Map<String,List<OpenEmoticion>> cache = (Map<String, List<OpenEmoticion>>) platformCache.hget(RedisPrefix._ADVERT_, type);
        List<OpenEmoticion> openEmoticions = null;
        if (!CollectionUtils.isEmpty(cache)) {
            openEmoticions = advertResultResolving(cache);
        }
        return openEmoticions;
    }

    public List<OpenEmoticion> advertResultResolving(Map<String, List<OpenEmoticion>> map) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        List<OpenEmoticion> result = new ArrayList<>();
        Long now = new Date().getTime();
        Set<String> keys = map.keySet();
        for (String s : keys) {
            String[] split = s.split("-");
            if ((now > Long.parseLong(split[0]) && now < Long.parseLong(split[1]))) {
                List<OpenEmoticion> openEmoticions = map.get(s);
                result.addAll(openEmoticions);
            }
        }
        return result;
    }
}
