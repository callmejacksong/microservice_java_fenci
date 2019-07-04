package com.melink.open.api.controller.website;

import com.melink.open.api.controller.AbstractListController;
import com.melink.open.api.service.BannerService;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.BannerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
public class BannerListController extends AbstractListController<BannerInfo> {

    @Autowired
    private BannerService bannerService;

    @GetMapping("/banner")
    public OpenApiV2ListResponse<BannerInfo> showBanner(){
        return executeApiCall(() -> {
            List<BannerInfo> banners = bannerService.getBanner();
            if (CollectionUtils.isEmpty(banners)) {
                return errorResponse("cannot find banner");
            }
            return successResponse(banners);
        });

    }
}
