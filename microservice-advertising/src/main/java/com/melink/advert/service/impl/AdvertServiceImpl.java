package com.melink.advert.service.impl;

import com.melink.advert.mapper.netPic.AdvertMapper;
import com.melink.advert.model.BasicNetPictureQuality;
import com.melink.advert.service.AdvertService;
import com.melink.microservice.cache.PlatformCache;
import com.melink.sop.api.models.RedisPrefix;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    private AdvertMapper advertMapper;
    @Autowired
    private PlatformCache<String, Object> platformCache;

    @Override
    public Map<String,List<OpenEmoticion>> getAdvertByType(String type) {

        //String key = RedisPrefix._ADVERT_ + type;
        Map<String,List<OpenEmoticion>> openEmoticions = (Map<String, List<OpenEmoticion>>) platformCache.hget(RedisPrefix._ADVERT_, type);
        if (!CollectionUtils.isEmpty(openEmoticions)) {
            return openEmoticions;
        }
        List<BasicNetPictureQuality> basicNetPictureQualitys = advertMapper.findGifByAdvertType(type);
        if (CollectionUtils.isEmpty(basicNetPictureQualitys)) {
            return openEmoticions;
        }
        openEmoticions = new HashMap<>();
        for (BasicNetPictureQuality basic : basicNetPictureQualitys) {
            OpenEmoticion openEmoticion = convertNetEmoticionForQuality(basic);
            Long start = basic.getStartTime().getTime();
            Long end = basic.getEndTime().getTime();
            List<OpenEmoticion> list = new ArrayList<>();
            String key = start + "-" + end;
            if (openEmoticions.containsKey(key)) {
                openEmoticions.get(key).add(openEmoticion);
            }else{
                list.add(openEmoticion);
                openEmoticions.put(key, list);
            }
        }
        platformCache.hset(RedisPrefix._ADVERT_, type, openEmoticions);

        return openEmoticions;
    }

    public static OpenEmoticion convertNetEmoticionForQuality(BasicNetPictureQuality basicEmoticon) {
        if (basicEmoticon == null) {
            return null;
        }
        OpenEmoticion emo = new OpenEmoticion();
        emo.setGuid(basicEmoticon.getnpId());
        emo.setWidth(basicEmoticon.getWidth());
        emo.setHeight(basicEmoticon.getHeight());
        emo.setAdvertPosition(basicEmoticon.getAdvertPosition());
        emo.setIsAdvert(true);
        //emo.setWeight(basicEmoticon.getWeight());
        emo.setLevel(basicEmoticon.getLevel());
        if(basicEmoticon == null){
            emo.setText("");
        }else{
            emo.setText(basicEmoticon.getName());
        }

        if (StringUtils.hasText(basicEmoticon.getLossyUrl())) {
            emo.setMain(basicEmoticon.getLossyUrl());
            emo.setThumb(basicEmoticon.getLossyUrl() + "?imageMogr2/thumbnail/!80p/format/png");
            emo.setFs(basicEmoticon.getLossyFsize());
            emo.setMd5(basicEmoticon.getLossyMd5());
        }else{
            emo.setMain(basicEmoticon.getStoreUrl());//没加上
            emo.setThumb(basicEmoticon.getStoreUrl() + "?imageMogr2/thumbnail/!80p/format/png");
            emo.setFs(basicEmoticon.getFsize());
            emo.setMd5(basicEmoticon.getMd5());
        }
        if (basicEmoticon.getStoreUrl().contains(".gif")) {
            emo.setIsAnimated("1");
        } else {
            emo.setIsAnimated("0");
        }
        if(basicEmoticon.getThumb() == null){
            emo.setGif_thumb(basicEmoticon.getLossyUrl());
        }else{
            emo.setGif_thumb(basicEmoticon.getThumb());
        }

        if(StringUtils.hasText(basicEmoticon.getWebp())){
            emo.setWebp(basicEmoticon.getWebp());
            emo.setWebpsize(basicEmoticon.getWebpsize());
            emo.setWebpmd5(basicEmoticon.getWebpmd5());
        }
        emo.setClassify(basicEmoticon.getClassify() == null ? 0:basicEmoticon.getClassify());
        emo.setKind(basicEmoticon.getKind());
        return emo;
    }
}
