package com.melink.open.api.controller.website;

import com.melink.microservice.cache.PlatformCache;
import com.melink.open.api.controller.AbstractListController;
import com.melink.open.api.service.SpecialNetKeywordService;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.RedisPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/netword")
public class SpecialNetKeywordListController extends AbstractListController<String> {
    @Autowired
    private PlatformCache<String, Object> platformCache;
    @Autowired
    private SpecialNetKeywordService specialNetKeywordService;

    @GetMapping("/common")
    public OpenApiV2ListResponse<String> findKeywordEmoji(
//            @RequestParam("timestamp") final String timestamp,
//            @RequestParam("signature") final String signature,
//            @RequestParam("app_id") final String appId
    ){
        return executeApiCall(() -> {
            List<String> infos = (List<String>) platformCache.getObject(RedisPrefix._OPEN_NETWORD_COMMON_TAG);
            if (CollectionUtils.isEmpty(infos)) {
                infos = specialNetKeywordService.getKeywordList();
                platformCache.putByte(RedisPrefix._OPEN_NETWORD_COMMON_TAG, infos);
            }
            return successResponse(infos);
        });
    }

}
