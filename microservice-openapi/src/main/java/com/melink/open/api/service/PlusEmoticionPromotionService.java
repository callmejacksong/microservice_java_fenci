package com.melink.open.api.service;

import com.melink.sop.api.models.open.modelinfos.EmoticionPromotion;
import com.melink.sop.api.models.open.modelinfos.PicturePromotionInfo;

import java.util.List;

public interface PlusEmoticionPromotionService {
   // List<EmoticionPromotion> detailPromotionByCodes(BatchQueryRequest queryReq, boolean sslRes,String appId);

    List<EmoticionPromotion> getPromotionByInfo( boolean sslRes,List<PicturePromotionInfo> picturePromotion);
}
