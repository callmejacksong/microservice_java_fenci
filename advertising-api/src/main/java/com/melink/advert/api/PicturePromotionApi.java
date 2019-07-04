package com.melink.advert.api;

import com.melink.sop.api.models.open.modelinfos.PicturePromotionInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PicturePromotionApi {
    @GetMapping("promotion/codes")
    List<PicturePromotionInfo> getPicturePromotion(@RequestParam("app_id") String appId,
                                                   @RequestParam("codes") List<String> codes,
                                                   @RequestParam("promotion_switch")Boolean promotionSwitch,
                                                   @RequestParam("global_promotion_switch")Boolean globalPromotionSwitch);

    @GetMapping("promotion/all")
    List<PicturePromotionInfo> getPicturePromotionAll(
            @RequestParam("app_id") String appId,
            @RequestParam("type") String type,
            @RequestParam("promotion_switch") Boolean promotionSwitch,
            @RequestParam("global_promotion_switch") Boolean globalPromotionSwitch);
}
