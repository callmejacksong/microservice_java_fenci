package com.melink.open.api.service.impl;

import com.melink.microservice.cache.PlatformCache;
import com.melink.open.api.service.StickerSearchService;
import com.melink.open.api.vo.SearchVO;
import com.melink.sop.api.models.RedisPrefix;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StickerSearchServiceImpl implements StickerSearchService {

    @Autowired
    private PlatformCache<String, Object> platformCache;


    private int tokenExpiresIn = 60 * 60 * 24;

    @Override
    public List<OpenEmoticion> searchEmoji(String timestamp, String signature,
                                                 boolean sslRes, SearchVO vo) {

        List<OpenEmoticion> emoticions = (List<OpenEmoticion>) platformCache.hget(RedisPrefix._OPEN_STICKER_SEARCH_TAG, vo.getQ().toLowerCase());

        return emoticions;
    }
}
