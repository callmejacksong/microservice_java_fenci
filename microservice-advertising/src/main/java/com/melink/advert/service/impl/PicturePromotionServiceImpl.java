package com.melink.advert.service.impl;


import com.melink.advert.mapper.netPic.PicturePromotionMapper;
import com.melink.advert.model.PicturePromotion;
import com.melink.advert.service.PicturePromotionService;
import com.melink.sop.api.models.open.modelinfos.PicturePromotionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PicturePromotionServiceImpl implements PicturePromotionService {

    @Autowired
    private PicturePromotionMapper picturePromotionMapper;

    @Override
    public List<PicturePromotionInfo> getPromotionByCodes(String appId,List<String> codes,Boolean promotionSwitch,Boolean globalPromotionSwitch) {
        //BeanCoperUtils instance = BeanCoperUtils.getInstance();
        List<PicturePromotionInfo> result = new ArrayList<>();
        List<PicturePromotion> picturePromotions = picturePromotionMapper.findPromotionByAppIdAndNpids(appId, codes,promotionSwitch,globalPromotionSwitch);
        for (PicturePromotion pic : picturePromotions) {
            PicturePromotionInfo info = new PicturePromotionInfo();
            //instance.copy(pic,info);
            picTOInfo(pic, info);
            result.add(info);
        }
        return result;
    }

    @Override
    public List<PicturePromotionInfo> getPromotionByType(String appId,String type, Boolean promotionSwitch, Boolean globalPromotionSwitch) {
        List<PicturePromotionInfo> result = new ArrayList<>();
        List<PicturePromotion> picturePromotions = picturePromotionMapper.findPromotionByAppIdAll(appId,type,promotionSwitch,globalPromotionSwitch);
        for (PicturePromotion pic : picturePromotions) {
            PicturePromotionInfo info = new PicturePromotionInfo();
            //instance.copy(pic,info);
            picTOInfo(pic, info);
            result.add(info);
        }
        return result;
    }

    private void picTOInfo(PicturePromotion pic, PicturePromotionInfo info) {
        info.setCreatetime(pic.getCreatetime());
        info.setGuid(pic.getGuid());
        info.setNpId(pic.getNpId());
        info.setPromotionBeginTime(pic.getPromotionBeginTime());
        info.setPromotionEndTime(pic.getPromotionEndTime());
        info.setPromotionIcon(pic.getPromotionIcon());
        info.setPromotionIconId(pic.getPromotionIconId());
        info.setPromotionVideoId(pic.getPromotionVideoId());
        info.setPromotionText(pic.getPromotionText());
        info.setPromotionTextColor(pic.getPromotionTextColor());
        info.setPromotionType(pic.getPromotionType());
        info.setPromotionUrl(pic.getPromotionUrl());
        info.setPosition(pic.getPosition());
    }
}

