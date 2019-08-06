package com.melink.open.api.controller;


import com.melink.microservice.json.JsonSerializer;
import com.melink.microservice.utils.ResultConverUtil;
import com.melink.microservice.utils.Tools;
import com.melink.open.api.annotation.NewRequiredAuth;
import com.melink.open.api.mapper.netPicMapper.KeywordNetMapper;
import com.melink.open.api.service.OpenEmoticionService;
import com.melink.open.api.util.SensitiveWordUtil;
import com.melink.open.api.vo.SearchVO;
import com.melink.sop.api.constant.ResultConvertUrlConstant.OpenEmoticionUrlConstant;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiPagination;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.melink.microservice.utils.Tools.subList;


@RestController
public class OpenEmoticonController extends AbstractListController<OpenEmoticion>{
    private static final Logger log = LoggerFactory.getLogger(OpenEmoticonController.class);
    @Autowired
    private OpenEmoticionService openEmoticionService;
    @Autowired
    private SensitiveWordUtil sensitiveWordUtil;
    @Autowired
    private ResultConverUtil resultConverUtil;

    @RequestMapping(value = "/gifs/search", produces = {"application/json;charset=UTF-8"})
    @NewRequiredAuth
    public String newSearchEmoji(//@RequestParam("timestamp") final String timestamp,
//                                 @RequestParam("signature") final String signature,
                                 @RequestParam("app_id") final String appId,
                                 @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
                                 @RequestParam(value = "partner", required = false, defaultValue = "") final String partner,
                                 HttpServletRequest request,
                                 @Valid final SearchVO vo, final BindingResult result) throws IOException {

        String resultStr = openEmoticionService.newSearchEmoji(appId, sslRes, partner, vo, result);
        return resultStr;
    }

    @RequestMapping(value = "/gifs/search_keyword", produces = {"application/json;charset=UTF-8"})
    public String netkeyword(//@RequestParam("timestamp") final String timestamp,
//                                 @RequestParam("signature") final String signature,
                                 @RequestParam("keyword") final String keyword,
                                 HttpServletRequest request) throws IOException {

        String resultStr = openEmoticionService.findKeyword(keyword);
        return resultStr;
    }

    @RequestMapping(value = "/gifs/fenci_result", produces = {"application/json;charset=UTF-8"})
    public String fencikeyword(//@RequestParam("timestamp") final String timestamp,
//                                 @RequestParam("signature") final String signature,
                             @RequestParam("keyword") final String keyword,
                             HttpServletRequest request) throws IOException {

        String resultStr = openEmoticionService.fenci(keyword);
        return resultStr;
    }

    @RequestMapping(value = "/gifs/search_fenci", produces = {"application/json;charset=UTF-8"})
    @NewRequiredAuth
    public String newSearchEmoji_2(//@RequestParam("timestamp") final String timestamp,
//                                 @RequestParam("signature") final String signature,
                                 @RequestParam("app_id") final String appId,
                                 @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
                                 @RequestParam(value = "partner", required = false, defaultValue = "") final String partner,
                                 HttpServletRequest request,
                                 @Valid final SearchVO vo, final BindingResult result) throws IOException {

        String resultStr = openEmoticionService.newSearchEmoji_2(appId, sslRes, partner, vo, result);
        return resultStr;
    }

    @RequestMapping(value = "/gifs/search_textinfo_fenci", produces = {"application/json;charset=UTF-8"})
    @NewRequiredAuth
    public String newSearchEmoji_3(//@RequestParam("timestamp") final String timestamp,
                                   //@RequestParam("signature") final String signature,
                                   @RequestParam("app_id") final String appId,
                                   @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
                                   @RequestParam(value = "partner", required = false, defaultValue = "") final String partner,
                                   HttpServletRequest request,
                                   @Valid final SearchVO vo, final BindingResult result) throws IOException {

        String resultStr = openEmoticionService.newSearchEmoji_3(appId, sslRes, partner, vo, result);
        return resultStr;
    }

    @RequestMapping(value = "/gifs/search_test", produces = {"application/json;charset=UTF-8"})
    @NewRequiredAuth
    public String newSearchEmoji_4(//@RequestParam("timestamp") final String timestamp,
                                   //@RequestParam("signature") final String signature,
                                   @RequestParam("app_id") final String appId,
                                   @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
                                   @RequestParam(value = "partner", required = false, defaultValue = "") final String partner,
                                   HttpServletRequest request,
                                   @Valid final SearchVO vo, final BindingResult result) throws IOException {

        String resultStr = openEmoticionService.newSearchEmoji_4(appId, sslRes, partner, vo, result);
        return resultStr;
    }

    @GetMapping("/mutli/search")
    public OpenApiV2ListResponse<OpenEmoticion> mutliSearch(
            @RequestParam("app_id") final String appId,
            @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
            @Valid final SearchVO vo, final BindingResult result){
        //mutliq传入参数时将特殊字符[]用encodeURI("")转换一下，此版本tomcat不接收原始[]符号
        return executeApiCall(() -> {
            if (!StringUtils.hasText(vo.getMutliq())) {
                return errorResponse(ErrorCode._INVALID_PARAMETER,"mutliq is valid");
            }
            List<String> wordList = JsonSerializer.newInstance().fromJson(vo.getMutliq(), List.class);
            if (CollectionUtils.isEmpty(wordList)) {
                return errorResponse(ErrorCode._INVALID_PARAMETER,"mutliq is valid");
            }

            List<String> newList = new ArrayList<>();
            //判断搜索词是否在敏感词中
            Iterator<String> iterator = wordList.iterator();
            while (iterator.hasNext()) {
                String word = iterator.next();
                if (!sensitiveWordUtil.isContains(word, appId)) {
                    newList.add(word);
                }
                if (newList.size() > 5) {
                    break;
                }
            }
            List<OpenEmoticion> emoticions = openEmoticionService.getMutliSearchResult(vo, false, newList);
            List<OpenEmoticion> list = Tools.subList(emoticions, vo.getSize(), vo.getP());
            OpenApiPagination openApiPagination = generatePagination(emoticions.size(), list.size(), Integer.parseInt(vo.getP()), Integer.parseInt(vo.getSize()));
            convertResult(list, sslRes, appId);
            return successResponse(list, openApiPagination);
        });
    }

    @GetMapping("/new/search")
    public OpenApiV2ListResponse<OpenEmoticion> search(
            @Valid final SearchVO vo,
            @RequestParam("app_id") final String appId,
            @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes
    ){
        return executeApiCall(() -> {
            if (!StringUtils.hasText(vo.getQ())) {
                return errorResponse(ErrorCode._INVALID_PARAMETER,"q is valid");
            }
            vo.setQ(vo.getQ().trim());
            if (StringUtils.hasText(vo.getQ()) && vo.getQ().length() > 20) {
                vo.setQ(vo.getQ().substring(0, 20));
            }
            if (!StringUtils.hasText(vo.getQ()) && vo.getQ().length() > 50) {
                return returnEmpty();
            }
            if (sensitiveWordUtil.isContains(vo.getQ(), appId)) {
                return returnEmpty();
            }
            List<OpenEmoticion> emoticions = openEmoticionService.newGetSearchResult(vo, new ArrayList<>(), false);
            List<OpenEmoticion> list = Tools.subList(emoticions, vo.getSize(), vo.getP());
            OpenApiPagination openApiPagination = generatePagination(emoticions.size(), list.size(), Integer.parseInt(vo.getP()), Integer.parseInt(vo.getSize()));
            convertResult(list, sslRes, appId);
            return successResponse(list, openApiPagination);
        });
    }

    @GetMapping("/only/search")
    public OpenApiV2ListResponse<OpenEmoticion> onlySearch(
            @RequestParam("q")String q,
            @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes
    ){
        return executeApiCall(() -> {
            Random r = new Random();

            List<OpenEmoticion> likeSearch = openEmoticionService.likeSearch(q);
            if (!CollectionUtils.isEmpty(likeSearch)) {
                OpenEmoticion openEmoticion = likeSearch.get(r.nextInt(likeSearch.size()));
                likeSearch = new ArrayList<>();
                likeSearch.add(openEmoticion);
                convertResult(likeSearch, sslRes, "");
                return successResponse(likeSearch);
            }
            List<OpenEmoticion> result = openEmoticionService.onlySearch(q);
            if (CollectionUtils.isEmpty(result)) {
                return null;
            }
            List<OpenEmoticion> list = subList(result, 5, 1);
            OpenEmoticion openEmoticion = list.get(r.nextInt(list.size()));
            list = new ArrayList<>();
            list.add(openEmoticion);

            convertResult(list, sslRes, "");
            return successResponse(list);
        });
    }

    @RequestMapping(value = "/gifs/text/info/search", produces = {"application/json;charset=UTF-8"})
    @NewRequiredAuth
    public OpenApiV2ListResponse<OpenEmoticion> searchEmojiTextInfo(@RequestParam("timestamp") final String timestamp,
                                 @RequestParam("signature") final String signature,
                                 @RequestParam("app_id") final String appId,
                                 @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
                                 @RequestParam(value = "partner", required = false, defaultValue = "") final String partner,
                                 HttpServletRequest request,
                                 @Valid final SearchVO vo, final BindingResult result) throws IOException {
        return executeApiCall(() -> {
            List<OpenEmoticion> emoticions = openEmoticionService.onlySearch(vo.getQ());
            if (CollectionUtils.isEmpty(emoticions)) {
                return successResponse(new ArrayList<>());
            }
            List<OpenEmoticion> emj = new ArrayList<>();
            List<OpenEmoticion> list = (List<OpenEmoticion>) subList(emoticions, vo.getSize(), vo.getP());
            OpenApiPagination openApiPagination = generatePagination(emoticions.size(), list.size(), Integer.parseInt(vo.getP()), Integer.parseInt(vo.getSize()));
            convertResult(list, sslRes, appId);
            for (OpenEmoticion o : list) {
                if (!StringUtils.isEmpty(o.getText())) {
                    emj.add(o);
                }
            }
            return successResponse(emj, openApiPagination);
        });
    }


    @GetMapping("/gifs/not/textinfo/search")
    public OpenApiV2ListResponse<OpenEmoticion> searchEmojiNotTextInfo(
            @RequestParam("q") String q,
            @RequestParam("size") Integer size
    ) throws IOException {

        return executeApiCall(() -> {
            List<OpenEmoticion> emoticions = openEmoticionService.onlySearch(q);

            if (CollectionUtils.isEmpty(emoticions)) {
                return successResponse(new ArrayList<>());
            }
            List<OpenEmoticion> emj = new ArrayList<>();
            for (OpenEmoticion openEmoticion : emoticions) {
                if (StringUtils.isEmpty(openEmoticion.getText())) {
                    emj.add(openEmoticion);
                }
                if (emj.size() == size) {
                    break;
                }
            }
            return successResponse(emj);
        });
    }


    private void convertResult(List<OpenEmoticion> list,boolean sslRes,String appId){
        ResultConverUtil.Convert convert = resultConverUtil.ConvertByAppId(sslRes, appId);
        for (OpenEmoticion o : list) {
            o.setIsAdvert(null);
            try {
                convert.converURL(o, OpenEmoticionUrlConstant.WEBP_URL,
                        OpenEmoticionUrlConstant.GIF_THUMB_URL, OpenEmoticionUrlConstant.MAIN_URL, OpenEmoticionUrlConstant.THUMB_URL);
            } catch (Exception e) {
                log.warn("openEmoticion URL Convert ERROR",e);
            }
        }
    }


    public OpenApiV2ListResponse returnEmpty() {
        OpenApiV2ListResponse<OpenEmoticion> em = successResponse(new ArrayList<>());
        return em;
    }
}