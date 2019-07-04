package com.melink.advert.controller;

import com.melink.advert.service.PicturePromotionService;
import com.melink.sop.api.models.open.modelinfos.PicturePromotionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping()
public class PicturePromotionController {

    @Autowired
    private PicturePromotionService picturePromotionService;

    @GetMapping("promotion/codes")
    public List<PicturePromotionInfo> getPicturePromotion(
            @RequestParam("app_id") String appId,
            @RequestParam("codes") List<String> codes,
            @RequestParam("promotion_switch")Boolean promotionSwitch,
            @RequestParam("global_promotion_switch")Boolean globalPromotionSwitch) {
        System.out.println("aaaa");
        return picturePromotionService.getPromotionByCodes(appId, codes,promotionSwitch,globalPromotionSwitch);
    }

    @GetMapping("promotion/all")
    public List<PicturePromotionInfo> getPicturePromotionAll(
            @RequestParam("app_id") String appId,
            @RequestParam("type") String type,
            @RequestParam("promotion_switch") Boolean promotionSwitch,
            @RequestParam("global_promotion_switch") Boolean globalPromotionSwitch) {

        System.out.println("aaaa");
        return picturePromotionService.getPromotionByType(appId, type, promotionSwitch, globalPromotionSwitch);
    }
}
