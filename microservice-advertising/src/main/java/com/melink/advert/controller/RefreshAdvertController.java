package com.melink.advert.controller;

import com.melink.advert.service.AdvertService;
import com.melink.microservice.cache.PlatformCache;
import com.melink.sop.api.constant.advertConstant.AdvertTypeConstant;
import com.melink.sop.api.models.RedisPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshAdvertController {

    @Autowired
    private AdvertService advertService;

    @Autowired
    private PlatformCache<String, Object> platformCache;

    @GetMapping("/refresh")
    public void refreshAdvert(){
        //刷新：将缓存清空，调用service方法，方法内会对空缓存重新添加缓存
        platformCache.hdel(RedisPrefix._ADVERT_, AdvertTypeConstant.ADVERT_TYPE_TRENDING);
        advertService.getAdvertByType(AdvertTypeConstant.ADVERT_TYPE_TRENDING);


        platformCache.hdel(RedisPrefix._ADVERT_,AdvertTypeConstant.ADVERT_TYPE_SEARCH);
        advertService.getAdvertByType(AdvertTypeConstant.ADVERT_TYPE_SEARCH);

    }
}
