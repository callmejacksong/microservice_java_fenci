package com.melink.open.api.controller;

import com.aliyuncs.utils.StringUtils;
import com.melink.microservice.utils.ResultConverUtil;
import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.service.GifsImageService;
import com.melink.open.api.service.OpenAppService;
import com.melink.sop.api.constant.ResultConvertUrlConstant.BasicNetPictureImageUrlConstant;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiV2DataResponse;
import com.melink.sop.api.models.open.modelinfos.BasicNetPictureInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("")
public class GifsImageDataController extends AbstractDataController<BasicNetPictureInfo> {
    private static final Logger log = LoggerFactory.getLogger(GifsImageDataController.class);
    @Autowired
    private GifsImageService gifsImageService;
    @Autowired
    private ResultConverUtil resultConverUtil;
    @Autowired
    private OpenAppService openAppService;

    @GetMapping("/gifs/{guid}")
    @NewRequiredAuth

    public OpenApiV2DataResponse<BasicNetPictureInfo> searchGifs(@PathVariable("guid") String guid,
                                                                 @RequestParam("app_id") final String appId,
                                                                 @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes) {
        return executeApiCall(()->{
            if (StringUtils.isEmpty(guid)) {
                return errorResponse(ErrorCode._INVALID_PARAMETER, "Gifs Data guid is null");
            }
            if (openAppService.getAppByCache(appId) == null) {
                return errorResponse(ErrorCode._SERVER_ERROR, "Cannot find the AppId");
            }

            BasicNetPictureInfo result = gifsImageService.searchGifs(guid);
            if (result == null) {
                return errorResponse(ErrorCode._INVALID_PARAMETER, "search Gifs Data is null");
            }
            try {
                resultConverUtil.ConvertByAppId(sslRes, appId)
                        .converURL(result.getImage().getOriginal(), BasicNetPictureImageUrlConstant.URL_URL)
                        .converURL(result.getImage().getMedium(), BasicNetPictureImageUrlConstant.URL_URL)
                        .converURL(result.getImage().getLarge(), BasicNetPictureImageUrlConstant.URL_URL)
                        .converURL(result.getImage().getSmall(), BasicNetPictureImageUrlConstant.URL_URL);
            } catch (Exception e) {
                log.warn("URL convert ERROR for BasicNetPictureInfo",e);
            }
            //HttpsResConvertUtils.listEmoticionConvertByAppId(result, sslRes, appId);
            return successResponse(result);

        });
    }


}
