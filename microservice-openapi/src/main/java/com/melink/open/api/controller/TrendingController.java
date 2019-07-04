package com.melink.open.api.controller;

import com.melink.microservice.utils.PlatformUtils;
import com.melink.microservice.utils.ResultConverUtil;
import com.melink.microservice.utils.Tools;
import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.feignClient.PicturePromotionClient;
import com.melink.open.api.model.OpenApp;
import com.melink.open.api.service.OpenAppService;
import com.melink.open.api.service.PlusEmoticionPromotionService;
import com.melink.open.api.service.TrendingService;
import com.melink.open.api.util.OpenEmoticionUtils;
import com.melink.open.api.util.SearchApiUtil;
import com.melink.open.api.vo.ValidaterVO;
import com.melink.sop.api.constant.ResultConvertUrlConstant.OpenEmoticionUrlConstant;
import com.melink.sop.api.constant.advertConstant.AdvertTypeConstant;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiPagination;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.EmoticionPromotion;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import com.melink.sop.api.models.open.modelinfos.PicturePromotionInfo;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("")
@RestController
public class TrendingController extends AbstractListController<OpenEmoticion> {
    private static final Logger log = LoggerFactory.getLogger(TrendingController.class);
    @Autowired
    private TrendingService trendingService;
    @Autowired
    private ResultConverUtil resultConverUtil;
    @Autowired
    private OpenAppService openAppService;
    @Autowired
    private PicturePromotionClient picturePromotionClient;
    @Autowired
    private PlusEmoticionPromotionService plusEmoticionPromotionService;

    @RequestMapping("/gifs/trending")
    @NewRequiredAuth
    public OpenApiV2ListResponse<OpenEmoticion> newTrendy(@RequestParam("timestamp") final String timestamp,
                                                          @RequestParam("signature") final String signature,
                                                          @RequestParam("app_id") final String appId,
                                                          @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
                                                          @Valid final ValidaterVO vo, final BindingResult result) {
        return executeApiCall(() -> {
            if (result.hasErrors()) {
                FieldError fieldError = result.getFieldError();
                return errorResponse(ErrorCode._INVALID_PARAMETER, fieldError.getDefaultMessage() + " is not valid");
            }
            OpenApp app = openAppService.getAppByCache(appId);
            List<PicturePromotionInfo> promotions = null;
            if (BooleanUtils.isTrue(app.getPromotionSwitch()) || BooleanUtils.isTrue(app.getGlobalPromotionSwitch())) {
                promotions = picturePromotionClient.getPicturePromotionAll(appId, AdvertTypeConstant.ADVERT_TYPE_TRENDING, app.getPromotionSwitch(), app.getGlobalPromotionSwitch());
            }
            List<EmoticionPromotion> emoticionPromotions = null;
            if (!CollectionUtils.isEmpty(promotions)) {
                emoticionPromotions = plusEmoticionPromotionService.getPromotionByInfo(sslRes, promotions);
            }

            List<OpenEmoticion> emoticions = trendingService.newTrendy(timestamp, signature, appId, sslRes, vo);

            if (!CollectionUtils.isEmpty(emoticionPromotions)) {
                emoticions = OpenEmoticionUtils.addPromotion(emoticions, emoticionPromotions);
            }

            List<OpenEmoticion> list = Tools.subList(emoticions, vo.getSize(), vo.getP());
            list = SearchApiUtil.trendReturnValue(list);

            OpenApiPagination openApiPagination = generatePagination(emoticions.size(), list.size(), PlatformUtils.toIntSafely(vo.getP(), 1), PlatformUtils.toIntSafely(vo.getSize(), 20));
            for (OpenEmoticion o : list) {
                try {
                    resultConverUtil.ConvertByAppId(sslRes, appId).converURL(o,
                            OpenEmoticionUrlConstant.GIF_THUMB_URL, OpenEmoticionUrlConstant.MAIN_URL,
                            OpenEmoticionUrlConstant.THUMB_URL, OpenEmoticionUrlConstant.WEBP_URL);
                } catch (Exception e) {
                    log.warn("URL convert ERROR for OpenEmoticion", e);
                }
            }

            OpenApiV2ListResponse<OpenEmoticion> openEmoticionOpenApiV2ListResponse = successResponse(list, openApiPagination);
            return openEmoticionOpenApiV2ListResponse;

        });
    }

    @RequestMapping("/text/info/trending")
    public OpenApiV2ListResponse<OpenEmoticion> textInfoTrending(
            @RequestParam("timestamp") final String timestamp,
            @RequestParam("signature") final String signature,
            @RequestParam("app_id") final String appId,
            @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
            @Valid final ValidaterVO vo, final BindingResult result
    ) {
        return executeApiCall(() -> {
            List<OpenEmoticion> emoticions = trendingService.textInfoTrending(true);
            if (CollectionUtils.isEmpty(emoticions)) {
                return successResponse(new ArrayList<>());
            }
            List<OpenEmoticion> list = Tools.subList(emoticions, vo.getSize(), vo.getP());
            list = SearchApiUtil.trendReturnValue(list);
            OpenApiPagination openApiPagination = generatePagination(emoticions.size(), list.size(), PlatformUtils.toIntSafely(vo.getP(), 1), PlatformUtils.toIntSafely(vo.getSize(), 20));
            for (OpenEmoticion o : list) {
                try {
                    resultConverUtil.ConvertByAppId(sslRes, appId).converURL(o,
                            OpenEmoticionUrlConstant.GIF_THUMB_URL, OpenEmoticionUrlConstant.MAIN_URL,
                            OpenEmoticionUrlConstant.THUMB_URL, OpenEmoticionUrlConstant.WEBP_URL);
                } catch (Exception e) {
                    log.warn("URL convert ERROR for OpenEmoticion", e);
                }
            }
            OpenApiV2ListResponse<OpenEmoticion> openEmoticionOpenApiV2ListResponse = successResponse(list, openApiPagination);
            return openEmoticionOpenApiV2ListResponse;
        });


    }

    @RequestMapping("/not/textinfo/trending")
    public OpenApiV2ListResponse<OpenEmoticion> notTextInfoTrending(
            @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
            @RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size
    ) {

        return executeApiCall(() -> {
            List<OpenEmoticion> emoticions = trendingService.textInfoTrending(false);
            if (CollectionUtils.isEmpty(emoticions)) {
                return successResponse(new ArrayList<>());
            }
            List<OpenEmoticion> list = Tools.subList(emoticions, size, p);
            list = SearchApiUtil.trendReturnValue(list);
            OpenApiPagination openApiPagination = generatePagination(emoticions.size(), list.size(), p, size);
            for (OpenEmoticion o : list) {
                try {
                    resultConverUtil.ConvertByAppId(sslRes, "").converURL(o,
                            OpenEmoticionUrlConstant.GIF_THUMB_URL, OpenEmoticionUrlConstant.MAIN_URL,
                            OpenEmoticionUrlConstant.THUMB_URL, OpenEmoticionUrlConstant.WEBP_URL);
                } catch (Exception e) {
                    log.warn("URL convert ERROR for OpenEmoticion", e);
                }
            }
            OpenApiV2ListResponse<OpenEmoticion> openEmoticionOpenApiV2ListResponse = successResponse(list, openApiPagination);
            return openEmoticionOpenApiV2ListResponse;
        });


    }
}
