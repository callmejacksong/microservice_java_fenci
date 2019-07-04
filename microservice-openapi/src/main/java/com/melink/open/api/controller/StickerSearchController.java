package com.melink.open.api.controller;

import com.melink.microservice.utils.PlatformUtils;
import com.melink.microservice.utils.ResultConverUtil;
import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.service.OpenAppService;
import com.melink.open.api.service.StickerSearchService;
import com.melink.open.api.vo.SearchVO;
import com.melink.sop.api.constant.ResultConvertUrlConstant.OpenEmoticionUrlConstant;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiPagination;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.melink.microservice.utils.Tools.subList;


@RequestMapping("/stickers")
@RestController
public class StickerSearchController extends AbstractListController<OpenEmoticion>{
    private static final Logger log = LoggerFactory.getLogger(StickerSearchController.class);
    @Autowired
    private StickerSearchService stickerSearchService;

    @Autowired
    private OpenAppService openAppService;

    @Autowired
    private ResultConverUtil resultConverUtil;

    @RequestMapping("/search")
    @NewRequiredAuth
    public OpenApiV2ListResponse<OpenEmoticion> searchEmoji(@RequestParam("timestamp") final String timestamp,
                                                            @RequestParam("signature") final String signature,
                                                            @RequestParam("app_id") final String appId,
                                                            @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
                                                            @Valid final SearchVO vo, final BindingResult result) {

        return executeApiCall(() -> {
            if (result.hasErrors()) {
                FieldError fieldError = result.getFieldError();
                return errorResponse(ErrorCode._INVALID_PARAMETER, fieldError.getDefaultMessage() + " is not valid");
            }

//            OpenApp app = openAppService.getAppByCache(appId);
//            if (app == null) {
//                return errorResponse(ErrorCode._INVALID_PARAMETER, "app_id not exist");
//            }
//            if(!app.getCopyrightPermisson()){
//                return errorResponse(ErrorCode._BAD_REQUEST,"app not support,No permisson");
//            }
            List<OpenEmoticion> emoticions = stickerSearchService.searchEmoji(timestamp, signature, sslRes, vo);
            if (emoticions == null) {
                emoticions = new ArrayList<>();
            }
            List<OpenEmoticion> list =(List<OpenEmoticion>) subList(emoticions, vo.getP(), vo.getSize());
            for (OpenEmoticion o : list) {
                try {
                    resultConverUtil.ConvertByAppId(sslRes, appId).converURL(o, OpenEmoticionUrlConstant.GIF_THUMB_URL, OpenEmoticionUrlConstant.WEBP_URL, OpenEmoticionUrlConstant.THUMB_URL, OpenEmoticionUrlConstant.MAIN_URL);
                } catch (Exception e) {
                    log.warn("URL convert ERROR for OpenEmoticion",e);
                }
            }
            OpenApiPagination openApiPagination = generatePagination(emoticions.size(), list.size(), PlatformUtils.toIntSafely(vo.getP(), 1), PlatformUtils.toIntSafely(vo.getSize(), 20));
            OpenApiV2ListResponse<OpenEmoticion> openApiV2ListResponse = successResponse((List<OpenEmoticion>) list, openApiPagination);
            return openApiV2ListResponse;
        });

    }
}
