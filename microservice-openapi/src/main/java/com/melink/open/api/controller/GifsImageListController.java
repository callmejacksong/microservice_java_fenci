package com.melink.open.api.controller;

import com.melink.microservice.utils.ResultConverUtil;
import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.service.GifsImageService;
import com.melink.open.api.service.OpenAppService;
import com.melink.sop.api.constant.ResultConvertUrlConstant.BasicNetPictureImageUrlConstant;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.BasicNetPictureInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class GifsImageListController extends AbstractListController<BasicNetPictureInfo> {
    private static final Logger log = LoggerFactory.getLogger(GifsImageListController.class);
    @Autowired
    private GifsImageService gifsImageService;
    @Autowired
    private ResultConverUtil resultConverUtil;
    @Autowired
    private OpenAppService openAppService;


    @GetMapping("gifs")
    @NewRequiredAuth
    public OpenApiV2ListResponse<BasicNetPictureInfo> searchGifsList(@RequestBody List<String> guids,
                                                                     @RequestParam("app_id") final String appId,
                                                                     @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes) {
        return executeApiCall(() -> {
            if (CollectionUtils.isEmpty(guids)) {
                return errorResponse(ErrorCode._INVALID_PARAMETER, "guids is null");
            }
            if (openAppService.getAppByCache(appId) == null) {
                return errorResponse(ErrorCode._SERVER_ERROR, "Cannot find the AppId");
            }
            List<BasicNetPictureInfo> result = gifsImageService.searchGifSList(guids);
            if (CollectionUtils.isEmpty(result)) {
                return errorResponse(ErrorCode._INVALID_PARAMETER, "not exist gifs");
            }
            for (BasicNetPictureInfo info : result) {
                try {
                    resultConverUtil.ConvertByAppId(sslRes, appId)
                            .converURL(info.getImage().getOriginal(), BasicNetPictureImageUrlConstant.URL_URL)
                            .converURL(info.getImage().getMedium(), BasicNetPictureImageUrlConstant.URL_URL)
                            .converURL(info.getImage().getLarge(), BasicNetPictureImageUrlConstant.URL_URL)
                            .converURL(info.getImage().getSmall(), BasicNetPictureImageUrlConstant.URL_URL);
                } catch (Exception e) {
                    log.warn("URL convert ERROR for BasicNetPictureInfo", e);
                }
            }
            return successResponse(result);
        });
    }
}
