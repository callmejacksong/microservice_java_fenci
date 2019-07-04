package com.melink.open.api.controller.website;

import com.melink.microservice.utils.ResultConverUtil;
import com.melink.microservice.utils.Tools;
import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.constant.AppConsts;
import com.melink.open.api.controller.AbstractListController;
import com.melink.open.api.service.HotGifService;
import com.melink.open.api.util.SearchApiUtil;
import com.melink.sop.api.constant.ResultConvertUrlConstant.OpenEmoticionUrlConstant;
import com.melink.sop.api.models.OpenApiPagination;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 官网热门动图
 */
@RestController
@RequestMapping("/hot")
public class HotGifListController extends AbstractListController<OpenEmoticion> {
    private static final Logger log = LoggerFactory.getLogger(HotGifListController.class);
    @Autowired
    private HotGifService hotGifService;
    @Autowired
    private ResultConverUtil resultConverUtil;

    @GetMapping("/gif")
    @NewRequiredAuth
    public OpenApiV2ListResponse<OpenEmoticion> hot(
            @RequestParam(value = "p", defaultValue = "1", required = false) final String p,
            @RequestParam(value = "size", defaultValue = "20", required = false) final String size
    ){
        return executeApiCall(() -> {
            //List<OpenEmoticion> hotGifs = (List<OpenEmoticion>) platformCache.getObject(RedisPrefix._OPEN_HOT_GIF_TAG);
            List<OpenEmoticion> Gifs = hotGifService.getHotOpenEmoticion(p,size);
            List<OpenEmoticion> openEmoticions = Tools.subList(Gifs, size, p);
            ResultConverUtil.Convert convert = resultConverUtil.ConvertByAppId(true, AppConsts.WEB);
            OpenApiPagination openApiPagination = generatePagination(Gifs.size(), openEmoticions.size(), Integer.parseInt(p), Integer.parseInt(size));
            for (OpenEmoticion o : openEmoticions) {
                try {
                    convert.converURL(o, OpenEmoticionUrlConstant.MAIN_URL,
                            OpenEmoticionUrlConstant.GIF_THUMB_URL, OpenEmoticionUrlConstant.WEBP_URL, OpenEmoticionUrlConstant.THUMB_URL);
                } catch (Exception e) {
                    log.warn("OpenEmoticion URL convert ERROR" ,e);
                }
            }
            openEmoticions = SearchApiUtil.OwReturnValue(openEmoticions);
            return successResponse(openEmoticions,openApiPagination);
        });
    }


}
