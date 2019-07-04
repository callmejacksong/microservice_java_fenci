package com.melink.open.api.service.impl;

import com.melink.open.api.feignClient.PicturePromotionClient;
import com.melink.open.api.mapper.melink.PromotionVideoMapper;
import com.melink.open.api.model.PromotionVideo;
import com.melink.open.api.service.PlusEmoticionPromotionService;
import com.melink.sop.api.models.open.modelinfos.EmoticionPromotion;
import com.melink.sop.api.models.open.modelinfos.PicturePromotionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlusEmoticionPromotionServiceImpl implements PlusEmoticionPromotionService {

    @Autowired
    private PromotionVideoMapper promotionVideoMapper;
    @Autowired
    private PicturePromotionClient picturePromotionClient;


    @Override
    public List<EmoticionPromotion> getPromotionByInfo( boolean sslRes, List<PicturePromotionInfo> picturePromotions) {

        if (CollectionUtils.isEmpty(picturePromotions)) {
            return null;
        }
        List<EmoticionPromotion> returned = new ArrayList<>();

        for (PicturePromotionInfo info : picturePromotions) {
            EmoticionPromotion emoticionPromotion = new EmoticionPromotion();
            emoticionPromotion.setSslRes(sslRes);
            emoticionPromotion.setGuid(info.getNpId());
            emoticionPromotion.setEmoCode(info.getNpId());
            emoticionPromotion.setPromotionBeginTime(info.getPromotionBeginTime() == null ? null:info.getPromotionBeginTime().getTime());
            emoticionPromotion.setPromotionEndTime(info.getPromotionEndTime() == null ? null:info.getPromotionEndTime().getTime());
            emoticionPromotion.setPromotionIcon(info.getPromotionIcon());
            emoticionPromotion.setPromotionUrl(info.getPromotionUrl());
            emoticionPromotion.setPromotionType(info.getPromotionType());
            emoticionPromotion.setPosition(info.getPosition());
            if(StringUtils.hasText(info.getPromotionTextColor())){
                emoticionPromotion.setPromotionTextColor(info.getPromotionTextColor());
            }
            if (info.getPromotionText() == null) {
                continue;
            }
            List<String> promotionTextList = new ArrayList<String>();
            String promotionText = info.getPromotionText();
            if (promotionText == null) {
                continue;
            }
            String[] splitArray = promotionText.split("\\|");
            for (String s : splitArray) {
                promotionTextList.add(s);
            }
            emoticionPromotion.setPromotionTextList(promotionTextList);
            if (StringUtils.hasText(info.getPromotionVideoId())) {
                PromotionVideo promotionVideo = promotionVideoMapper.findByGuid(info.getPromotionVideoId());
                if (promotionVideo == null){
                    continue;
                }
                emoticionPromotion.setPromotionVideoTitle(promotionVideo.getTitle());
                emoticionPromotion.setPromotionVideoAuthorIcon(promotionVideo.getAuthorIcon());
                emoticionPromotion.setPromotionVideoAuthorName(promotionVideo.getAuthorName());
                emoticionPromotion.setPromotionVideoDsecription(promotionVideo.getDescription());
                emoticionPromotion.setPromotionVideoHeight(promotionVideo.getHeight());
                emoticionPromotion.setPromotionVideoWidth(promotionVideo.getWidth());
                if (StringUtils.hasText(promotionVideo.getDetailUrl())) {
                    emoticionPromotion.setPromotionVideoDetailUrl(promotionVideo.getDetailUrl());
                }
            }
            returned.add(emoticionPromotion);
        }
        return returned;
    }

}

