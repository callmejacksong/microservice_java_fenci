package com.melink.advert.service;

import com.melink.sop.api.models.open.modelinfos.PicturePromotionInfo;

import java.util.List;

public interface PicturePromotionService {
    List<PicturePromotionInfo> getPromotionByCodes(String appId, List<String> codes,Boolean promotionSwitch,Boolean globalPromotionSwitch);

    List<PicturePromotionInfo> getPromotionByType(String appId,String type, Boolean promotionSwitch, Boolean globalPromotionSwitch);
}
