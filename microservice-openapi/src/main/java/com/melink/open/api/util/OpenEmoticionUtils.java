package com.melink.open.api.util;

import com.melink.sop.api.models.open.modelinfos.EmoticionPromotion;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class OpenEmoticionUtils {

    public static List<OpenEmoticion> addPromotion(List<OpenEmoticion> emoticions, List<EmoticionPromotion> promotions) {

        Map<String, EmoticionPromotion> promotionMap = null;

        if (!CollectionUtils.isEmpty(promotions) && !CollectionUtils.isEmpty(emoticions)) {
            promotionMap = promotions.stream().collect(Collectors.toMap(EmoticionPromotion::getGuid, info -> info));
        }

        List<OpenEmoticion> openEmoticions = new ArrayList<>();
        List<OpenEmoticion> promotionList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(promotionMap)) {
            for (OpenEmoticion emoticion : emoticions) {
                EmoticionPromotion promotion = promotionMap.get(emoticion.getGuid());
                if (promotion != null) {
                    emoticion.setPromotion(promotion);
                    if (promotion.getPosition() == null) {
                        openEmoticions.add(emoticion);
                    } else {
                        promotionList.add(emoticion);
                    }
                } else {
                    openEmoticions.add(emoticion);
                }
            }
        }

        if (!CollectionUtils.isEmpty(promotionList)) {
            Collections.sort(promotionList, new Comparator<OpenEmoticion>() {
                @Override
                public int compare(OpenEmoticion o1, OpenEmoticion o2) {
                    Integer n = o1.getPromotion().getPosition() - o2.getPromotion().getPosition();
                    return n;
                }
            });
            for (OpenEmoticion emoticion : promotionList) {
                openEmoticions.add(emoticion.getPromotion().getPosition(), emoticion);
            }
        }

        if (!CollectionUtils.isEmpty(openEmoticions)) {
            return openEmoticions;
        }
        return emoticions;
    }

    public static void resultAddAdvert(List<OpenEmoticion> result , List<OpenEmoticion> adverts){
        for (OpenEmoticion o : adverts) {
            //判断 广告 要添加的位置有没有超过数据的长度
            int p = o.getAdvertPosition() - 1;
            if (p >= result.size()) {
                result.add(o);
            } else if (p < 0) {
                result.add(0,o);
            } else {
                result.add(p, o);
            }
            o.setAdvertPosition(null);
        }
    }
}
