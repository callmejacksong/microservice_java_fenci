package com.melink.open.api.service.impl;

import com.melink.microservice.cache.PlatformCache;
import com.melink.microservice.json.JsonSerializer;
import com.melink.microservice.utils.PlatformUtils;
import com.melink.microservice.utils.ResultConverUtil;
import com.melink.open.api.constant.AppConsts;
import com.melink.open.api.constant.FmtConsts;
import com.melink.open.api.constant.SearchTypeConsts;
import com.melink.open.api.feignClient.AdvertClient;
import com.melink.open.api.feignClient.PicturePromotionClient;
import com.melink.open.api.mapper.netPicMapper.BasicNetPictureQualityMapper;
import com.melink.open.api.mapper.netPicMapper.FenCiKeywordEmoticonMapper;
import com.melink.open.api.mapper.netPicMapper.KeywordNetMapper;
import com.melink.open.api.model.BasicNetPicture;
import com.melink.open.api.model.BasicNetPictureQuality;
import com.melink.open.api.model.NetKeyword;
import com.melink.open.api.model.OpenApp;
import com.melink.open.api.service.OpenAppService;
import com.melink.open.api.service.OpenEmoticionService;
import com.melink.open.api.service.PlusEmoticionPromotionService;
import com.melink.open.api.util.*;
import com.melink.open.api.vo.SearchVO;
import com.melink.sop.api.constant.ResultConvertUrlConstant.OpenEmoticionUrlConstant;
import com.melink.sop.api.constant.advertConstant.AdvertTypeConstant;
import com.melink.sop.api.models.ErrorCode;
import com.melink.sop.api.models.OpenApiPagination;
import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.RedisPrefix;
import com.melink.sop.api.models.open.modelinfos.EmoticionPromotion;
import com.melink.sop.api.models.open.modelinfos.NetKeywordRelationInfo;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import com.melink.sop.api.models.open.modelinfos.PicturePromotionInfo;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.melink.microservice.utils.Tools.subList;
import static com.melink.open.api.mapper.netPicMapper.FenCiKeywordEmoticonMapper.*;


@Service
public class OpenEmoticionServiceImpl implements OpenEmoticionService {
    private static final Logger log = LoggerFactory.getLogger(OpenEmoticionServiceImpl.class);
    @Autowired
    private TaobaoWhitelistUtil taobaoWhitelistUtil;

    @Autowired
    private SensitiveWordUtil sensitiveWordUtil;

    @Autowired
    private PlatformCache<String, Object> platformCache;

    @Autowired
    private PlatformCache<String, Object> resultCache;

    @Autowired
    private OpenAppService openAppService;

    @Autowired
    private ResponseUtil<OpenEmoticion> responseUtil;

    @Autowired
    private ResponseUtil2<BasicNetPicture> responseUtil2;
    @Autowired
    private ResultConverUtil resultConverUtil;
    //广告接口 api
    @Autowired
    private AdvertClient advertClient;

    @Autowired
    private BasicNetPictureQualityMapper basicNetPictureQualityMapper;

    @Autowired
    private PicturePromotionClient picturePromotionClient;

    @Autowired
    private PlusEmoticionPromotionService plusEmoticionPromotionService;

    @Autowired
    private FenCiKeywordEmoticonMapper fenCiKeywordEmoticonMapper;

    @Autowired
    private KeywordNetMapper keywordNetMapper;



    @Override
    public String newSearchEmoji(String appId, boolean sslRes, String partner, SearchVO vo, BindingResult result) {
        boolean allowCopyRight = false;
        vo.setQ(vo.getQ().trim());
        if (result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            return responseUtil.errorResponseToStr(ErrorCode._INVALID_PARAMETER, fieldError.getDefaultMessage() + " is not valid");
        }

        if (!StringUtils.hasText(vo.getQ()) || vo.getQ().length() > 20) {
            return returnEmpty();
        }
        //判断搜索词是否在敏感词中
        if (sensitiveWordUtil.isContains(vo.getQ(), appId)) {
            return returnEmpty();
        }

        //旺旺白名单
        if (isTaobaoWhitelist(vo.getQ(), appId)) {
            return returnEmpty();
        }
        JsonSerializer jsonSerializer = JsonSerializer.newInstance();

        //获取广告数据 adverts
//        List<OpenEmoticion> adverts = advertClient.getAdvertByType(AdvertTypeConstant.ADVERT_TYPE_SEARCH);
//        List<OpenEmoticion> adverts = new ArrayList<OpenEmoticion>();


        String resultStr = (String) resultCache.hgetStr(RedisPrefix._OPEN_RESULT_CACHE_V2 + vo.getQ().toLowerCase(), allowCopyRight + "_" + appId + "_" + vo.getFs() + "_" + sslRes + "_" + vo.getP() + "_" + vo.getSize() + "_" + vo.getFs_limit() + "_" + vo.getGt_min() + "_" + vo.getGt_max() + "_" + vo.getGt_crop() + "_" + vo.getEx_fmt() + "_" + vo.getType());
        if (StringUtils.hasText(resultStr)) {
            resultCache.setExpire(RedisPrefix._OPEN_RESULT_CACHE + vo.getQ().toLowerCase(), 300);
            return resultStr;
        }
        List<String> allKeyword = new ArrayList<String>();
        List<OpenEmoticion> emoticions = new ArrayList<OpenEmoticion>();

        if (openAppService.isTestApp(appId)) {
            emoticions = (List<OpenEmoticion>) platformCache.hget(RedisPrefix._OPEN_SEARCH_TEST_TOKEN, vo.getQ());
            if (emoticions == null) {
                emoticions = new ArrayList<OpenEmoticion>();
            }
        } else {
            emoticions = getSearchResult(appId, vo, allKeyword, allowCopyRight);
        }
        return resultCache(newGetObjectApiResponseObject(vo, sslRes, emoticions, appId, allKeyword), vo, sslRes, appId, allowCopyRight);

    }

    private List<BasicNetPicture> getSearchResultByQuerySQL( String text) {
        List<BasicNetPicture> u_emoticons = new ArrayList<BasicNetPicture>();
        if(fenCiKeywordEmoticonMapper.checkKeywordCountByText(text)>0){
            List<BasicNetPicture> temp_pics_main=fenCiKeywordEmoticonMapper.findNetPictureByKeyword(text);
            List<NetKeyword> relationWords=fenCiKeywordEmoticonMapper.findRelationWordsByKeyword(text);
            u_emoticons.addAll(temp_pics_main);
            HashSet<BasicNetPicture> set = new HashSet<BasicNetPicture>();
//            for(BasicNetPicture pi:u_emoticons){
//                pi.setWeight(5);
//            }
            List<BasicNetPicture> muti_emoticons = new ArrayList<BasicNetPicture>();
            for(NetKeyword reword:relationWords){
                List<BasicNetPicture> tmp_pics = fenCiKeywordEmoticonMapper.findNetPictureByKeyword(reword.getText());
                for(BasicNetPicture pi:tmp_pics){
                    if(set.add(pi)){
//                        pi.setWeight(3);
                        u_emoticons.add(pi);
                    }
                    else{
                        muti_emoticons.add(pi);
                    }

                }
            }
            for(BasicNetPicture pi:u_emoticons){
                for(BasicNetPicture pi_1:muti_emoticons){
                    if(pi.getGuid()==pi_1.getGuid()){
                        pi.setWeight(pi.getWeight()+100);
                    }
                }
            }

        }else{
            List<String> words = new ArrayList<String>();
            List<String> segments = WordUtils.segment(text);
            if (segments.size() > 5) {
                words.addAll(new ArrayList<String>(segments.subList(0, 5)));
            } else {
                words.addAll(segments);
            }
            List<BasicNetPicture> muti_emoticons = new ArrayList<BasicNetPicture>();
            HashSet<BasicNetPicture> set = new HashSet<BasicNetPicture>();

            for(String reword:words){
                List<BasicNetPicture> tmp_pics = fenCiKeywordEmoticonMapper.findNetPictureByKeyword(reword);
                for(BasicNetPicture pi:tmp_pics){
                    if(set.add(pi)){
//                        pi.setWeight(3);
                        u_emoticons.add(pi);
                    }
                    else{
                        muti_emoticons.add(pi);
                    }

                }
            }
            for(BasicNetPicture pi:u_emoticons){
                for(BasicNetPicture pi_1:muti_emoticons){
                    if(pi.getGuid()==pi_1.getGuid()){
                        pi.setWeight(pi.getWeight()+100);
                    }
                }
            }

        }
        Collections.sort(u_emoticons,new Comparator<BasicNetPicture>() {
                    public int compare(BasicNetPicture arg0, BasicNetPicture arg1) {
                        return arg1.getWeight()-arg0.getWeight();
                    }
                }
        );


        for (BasicNetPicture emo:u_emoticons){
            System.out.println(emo.getWeight());
        }

        return u_emoticons;
    }

    private List<BasicNetPicture> getSearchResult_main_3(String appId, SearchVO vo,String text, List<String> allKeyword, boolean allowCopyRight) {
        String cacheKey = choiceCacheKey(vo.getFs());
        List<String> words = new ArrayList<String>();
        List<BasicNetPicture> u_emoticons = new ArrayList<BasicNetPicture>();
        List<String> segments = WordUtils.segment(text);
        if (segments.size() > 5) {
            words.addAll(new ArrayList<String>(segments.subList(0, 5)));
        } else {
            words.addAll(segments);
        }

        HashSet<BasicNetPicture> set = new HashSet<BasicNetPicture>();
        List<BasicNetPicture> emos_1 = getSearchResultByQuerySQL(text);
        for (BasicNetPicture emo:emos_1){
            if (set.add(emo)) {
                emo.setWeight(emo.getWeight()+3);
                u_emoticons.add(emo);
            }
        }
        List<BasicNetPicture> emos_muti = new ArrayList<BasicNetPicture>();
        for (String te : words) {
            List<BasicNetPicture> emos = getSearchResultByQuerySQL(te);
            for (BasicNetPicture emo:emos){
                if (set.add(emo)) {
                    emo.setWeight(emo.getWeight()+2);
                    u_emoticons.add(emo);
                }else{
                    emos_muti.add(emo);
                }

            }
        }
        for (BasicNetPicture emo:u_emoticons){
            for (BasicNetPicture emo_1:emos_muti){
                if (emo.getGuid()==emo_1.getGuid()) {
                    emo.setWeight(emo.getWeight()+1);
                }
            }
        }


        Collections.sort(u_emoticons,new Comparator<BasicNetPicture>() {
                    public int compare(BasicNetPicture arg0, BasicNetPicture arg1) {
                        return arg1.getWeight()-arg0.getWeight();
                    }
                }
        );
        System.out.println("main_3开始了");

        for (BasicNetPicture emo:u_emoticons){
            emo.setFenciWords(words);
            emo.setText(emo.getTextinfo());
            System.out.println(emo.getWeight());
        }
        System.out.println("main_3完成了");

        return u_emoticons;
    }






    @Override
    public String newSearchEmoji_3(String appId, boolean sslRes, String partner, SearchVO vo, BindingResult result) {
        boolean allowCopyRight = false;
        vo.setQ(vo.getQ().trim());
        if (result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            return responseUtil.errorResponseToStr(ErrorCode._INVALID_PARAMETER, fieldError.getDefaultMessage() + " is not valid");
        }

        if (!StringUtils.hasText(vo.getQ()) || vo.getQ().length() > 20) {
            return returnEmpty();
        }
        //判断搜索词是否在敏感词中
        if (sensitiveWordUtil.isContains(vo.getQ(), appId)) {
            return returnEmpty();
        }

        //旺旺白名单
        if (isTaobaoWhitelist(vo.getQ(), appId)) {
            return returnEmpty();
        }
        JsonSerializer jsonSerializer = JsonSerializer.newInstance();

        //获取广告数据 adverts
//        List<OpenEmoticion> adverts = advertClient.getAdvertByType(AdvertTypeConstant.ADVERT_TYPE_SEARCH);
//        List<OpenEmoticion> adverts = new ArrayList<OpenEmoticion>();


        String resultStr = (String) resultCache.hgetStr(RedisPrefix._OPEN_RESULT_CACHE_V2 + vo.getQ().toLowerCase(), allowCopyRight + "_" + appId + "_" + vo.getFs() + "_" + sslRes + "_" + vo.getP() + "_" + vo.getSize() + "_" + vo.getFs_limit() + "_" + vo.getGt_min() + "_" + vo.getGt_max() + "_" + vo.getGt_crop() + "_" + vo.getEx_fmt() + "_" + vo.getType());
        if (StringUtils.hasText(resultStr)) {
            resultCache.setExpire(RedisPrefix._OPEN_RESULT_CACHE + vo.getQ().toLowerCase(), 300);
            return resultStr;
        }
        List<String> allKeyword = new ArrayList<String>();

        List<BasicNetPicture> emoticions = getSearchResult_main_3(appId, vo,vo.getQ(), allKeyword, allowCopyRight);
        return resultCache(newGetObjectApiResponseObjectSql(vo, sslRes, emoticions, appId, allKeyword), vo, sslRes, appId, allowCopyRight);

    }


    @Override
    public String newSearchEmoji_4(String appId, boolean sslRes, String partner, SearchVO vo, BindingResult result) {
        boolean allowCopyRight = false;
        vo.setQ(vo.getQ().trim());
//        if (result.hasErrors()) {
//            FieldError fieldError = result.getFieldError();
//            return responseUtil.errorResponseToStr(ErrorCode._INVALID_PARAMETER, fieldError.getDefaultMessage() + " is not valid");
//        }
//
//        if (!StringUtils.hasText(vo.getQ()) || vo.getQ().length() > 20) {
//            return returnEmpty();
//        }
//        //判断搜索词是否在敏感词中
//        if (sensitiveWordUtil.isContains(vo.getQ(), appId)) {
//            return returnEmpty();
//        }
//
//        //旺旺白名单
//        if (isTaobaoWhitelist(vo.getQ(), appId)) {
//            return returnEmpty();
//        }

        List<BasicNetPicture> resultList = fenCiKeywordEmoticonMapper.findNetPictureByKeyword(vo.getQ());
        JsonSerializer jsonSerializer = JsonSerializer.newInstance();
        String s = jsonSerializer.toJson(resultList);




        //获取广告数据 adverts
//        List<OpenEmoticion> adverts = advertClient.getAdvertByType(AdvertTypeConstant.ADVERT_TYPE_SEARCH);
//        List<OpenEmoticion> adverts = new ArrayList<OpenEmoticion>();


//        String resultStr = (String) resultCache.hgetStr(RedisPrefix._OPEN_RESULT_CACHE_V2 + vo.getQ().toLowerCase(), allowCopyRight + "_" + appId + "_" + vo.getFs() + "_" + sslRes + "_" + vo.getP() + "_" + vo.getSize() + "_" + vo.getFs_limit() + "_" + vo.getGt_min() + "_" + vo.getGt_max() + "_" + vo.getGt_crop() + "_" + vo.getEx_fmt() + "_" + vo.getType());
//        if (StringUtils.hasText(resultStr)) {
//            resultCache.setExpire(RedisPrefix._OPEN_RESULT_CACHE + vo.getQ().toLowerCase(), 300);
//            return resultStr;
//        }
//        List<String> allKeyword = new ArrayList<String>();

//        List<BasicNetPicture> emoticions = getSearchResult_main_3(appId, vo,vo.getQ(), allKeyword, allowCopyRight);
//        return resultCache(newGetObjectApiResponseObjectSql(vo, sslRes, emoticions, appId, allKeyword), vo, sslRes, appId, allowCopyRight);
          return s;
    }

    @Override
    public String findKeyword(String keyword) {

        NetKeyword result_ = keywordNetMapper.findKeyword(keyword.trim());
        JsonSerializer jsonSerializer = JsonSerializer.newInstance();
        String s = jsonSerializer.toJson(result_);

        return s;
    }





    private List<OpenEmoticion> getSearchResult_main(String appId, SearchVO vo,String wo, List<String> allKeyword, boolean allowCopyRight) {
        String cacheKey = choiceCacheKey(vo.getFs());
        List<String> words = new ArrayList<String>();
        List<OpenEmoticion> u_emoticons = new ArrayList<OpenEmoticion>();
        NetKeywordRelationInfo keywordRelationInfo = searchWordCache(wo);
        List<String> segments = WordUtils.segment(wo);
        if (segments.size() > 5) {
            words.addAll(new ArrayList<String>(segments.subList(0, 5)));
        } else {
            words.addAll(segments);
        }
//        keywordRelationInfo = segmentWordDeal(wo, words);
//        words.add(0,vo.getQ());
        HashSet<OpenEmoticion> set = new HashSet<OpenEmoticion>();
        List<OpenEmoticion> emos_1 = getSearchResult_2(appId, vo,wo,  allKeyword,allowCopyRight);
        for (OpenEmoticion emo:emos_1){
            if (set.add(emo)) {
                emo.setWeight(emo.getWeight()+3);
                u_emoticons.add(emo);
            }
        }
        List<OpenEmoticion> emos_muti = new ArrayList<OpenEmoticion>();
        for (String te : words) {
            List<OpenEmoticion> emos = getSearchResult_2(appId, vo,te,  allKeyword,allowCopyRight);
            for (OpenEmoticion emo:emos){
                if (set.add(emo)) {
                    emo.setWeight(emo.getWeight()+2);
                    u_emoticons.add(emo);
                }else{
                    emos_muti.add(emo);
                }

            }
        }
        for (OpenEmoticion emo:u_emoticons){
            for (OpenEmoticion emo_1:emos_muti){
                if (emo.getGuid()==emo_1.getGuid()) {
                    emo.setWeight(emo.getWeight()+emo_1.getWeight());
                }
            }
        }

//
//        for (String te : words) {
//            for (OpenEmoticion emo:u_emoticons){
//                if (emo.getKeywords().contains(te)) {
//                    emo.setWeight(emo.getWeight()+1);
//                }
//            }
//        }
        Collections.sort(u_emoticons,new Comparator<OpenEmoticion>() {
                    public int compare(OpenEmoticion arg0, OpenEmoticion arg1) {
                        return arg1.getWeight()-arg0.getWeight();
                    }
                }
                );

        System.out.println("main2开始了。。。");

        for (OpenEmoticion emo:u_emoticons){
            emo.setFenciWords(words);
            System.out.println(emo.getWeight());
            }
        System.out.println("main2完成了。。。");


        return u_emoticons;
    }


    @Override
    public String newSearchEmoji_2(String appId, boolean sslRes, String partner, SearchVO vo, BindingResult result) {
        boolean allowCopyRight = false;
        vo.setQ(vo.getQ().trim());
        if (result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            return responseUtil.errorResponseToStr(ErrorCode._INVALID_PARAMETER, fieldError.getDefaultMessage() + " is not valid");
        }

        if (!StringUtils.hasText(vo.getQ()) || vo.getQ().length() > 20) {
            return returnEmpty();
        }
        //判断搜索词是否在敏感词中
        if (sensitiveWordUtil.isContains(vo.getQ(), appId)) {
            return returnEmpty();
        }

        //旺旺白名单
        if (isTaobaoWhitelist(vo.getQ(), appId)) {
            return returnEmpty();
        }
        JsonSerializer jsonSerializer = JsonSerializer.newInstance();

        //获取广告数据 adverts
//        List<OpenEmoticion> adverts = advertClient.getAdvertByType(AdvertTypeConstant.ADVERT_TYPE_SEARCH);
//        List<OpenEmoticion> adverts = new ArrayList<OpenEmoticion>();


        String resultStr = (String) resultCache.hgetStr(RedisPrefix._OPEN_RESULT_CACHE_V2 + vo.getQ().toLowerCase(), allowCopyRight + "_" + appId + "_" + vo.getFs() + "_" + sslRes + "_" + vo.getP() + "_" + vo.getSize() + "_" + vo.getFs_limit() + "_" + vo.getGt_min() + "_" + vo.getGt_max() + "_" + vo.getGt_crop() + "_" + vo.getEx_fmt() + "_" + vo.getType());
        if (StringUtils.hasText(resultStr)) {
            resultCache.setExpire(RedisPrefix._OPEN_RESULT_CACHE + vo.getQ().toLowerCase(), 300);
            return resultStr;
        }
        List<String> allKeyword = new ArrayList<String>();
        List<OpenEmoticion> emoticions = new ArrayList<OpenEmoticion>();

        if (openAppService.isTestApp(appId)) {
            emoticions = (List<OpenEmoticion>) platformCache.hget(RedisPrefix._OPEN_SEARCH_TEST_TOKEN, vo.getQ());
            if (emoticions == null) {
                emoticions = new ArrayList<OpenEmoticion>();
            }
        } else {
            emoticions = getSearchResult_main(appId, vo,vo.getQ(), allKeyword, allowCopyRight);
        }

        return resultCache(newGetObjectApiResponseObject(vo, sslRes, emoticions, appId, allKeyword), vo, sslRes, appId, allowCopyRight);

    }



    private List<OpenEmoticion> getSearchResult_2(String appId, SearchVO vo,String wo, List<String> allKeyword, boolean allowCopyRight) {
        String cacheKey = choiceCacheKey(vo.getFs());
        List<String> words = new ArrayList<String>();
        //搜索关键词表
        NetKeywordRelationInfo keywordRelationInfo = searchWordCache(wo);
        Map<String, List<String>> wordMap = null;
        if (keywordRelationInfo != null) {
            //如果关键词类型为C1,类别则再查询一层
            if ((keywordRelationInfo.getType() & NetKeywordRelationInfo.C1) > 0) {
                relationWordSearch(keywordRelationInfo.getRelationWords());
            }
        } else {
            //不属于关键词,分词逻辑
            keywordRelationInfo = segmentWordDeal(wo, words);
        }
        //关键词图片查询
        allKeyword.addAll(wordDealReturnAllKeyword(keywordRelationInfo, cacheKey, allowCopyRight));
        //经过关联词逻辑,但是没有图片的情况,要走一遍分词
        if (isEmpty(keywordRelationInfo) && keywordRelationInfo.getType() != NetKeywordRelationInfo.C4) {
            keywordRelationInfo = segmentWordDeal(vo.getQ(), words);
            allKeyword.addAll(wordDealReturnAllKeyword(keywordRelationInfo, cacheKey, allowCopyRight));
        }
        wordMap = generateWordMap(keywordRelationInfo);
        if (!AppConsts.MOMO.equals(appId) && !AppConsts.ZUJI.equals(appId) && !AppConsts.DINGDING.equals(appId)) {
            classifyGroupOrderDeal(keywordRelationInfo);
        }
        List<OpenEmoticion> openEmoticions = imageGroup(keywordRelationInfo);
        if (openEmoticions == null) {
            openEmoticions = new ArrayList<OpenEmoticion>();
        }
        if (AppConsts.MOMO.equals(appId) || AppConsts.ZUJI.equals(appId)) {
            openEmoticions = classifyGroupOrder(openEmoticions, keywordRelationInfo.getText(), appId);
        }
        if (keywordRelationInfo.getType() != NetKeywordRelationInfo.C4) {
            removeuplicates(openEmoticions);
        } else {
            topMutliKw(openEmoticions, wordMap);
        }
        return openEmoticions;
    }


    @Override
    public List<OpenEmoticion> getMutliSearchResult(SearchVO vo, boolean allowCopyRight, List<String> wordList) {
        String cacheKey = choiceCacheKey(vo.getFs());
        List<NetKeywordRelationInfo> keywordInfos = new ArrayList<>();
        if(CollectionUtils.isEmpty(wordList)){
            return new ArrayList<OpenEmoticion>();
        }
        //搜索所有的关键字
        for (String word : wordList) {
            NetKeywordRelationInfo keywordRelationInfo = searchWordCache(word);
            if (keywordRelationInfo != null) {
                keywordRelationInfo.setRelationWords(null);
                keywordInfos.add(keywordRelationInfo);
            }
        }
        //将第一个词提出来
        NetKeywordRelationInfo keywordRelationInfo = keywordInfos.get(0);
        keywordInfos.remove(0);
        keywordRelationInfo.setRelationWords(keywordInfos);
        //搜索词下的表情
        wordDealReturnAllKeyword2(keywordRelationInfo, cacheKey, allowCopyRight);
        List<OpenEmoticion> openEmoticions = imageGroup(keywordRelationInfo);
        if (openEmoticions == null) {
            openEmoticions = new ArrayList<OpenEmoticion>();
        }
        removeuplicatesForExistKeywords(openEmoticions, wordList);
        return openEmoticions;
    }

    @Override
    public List<OpenEmoticion> newGetSearchResult(SearchVO vo, List<String> allKeyword, boolean allowCopyRight) {
        String cacheKey = choiceCacheKey(vo.getFs());
        List<String> words = new ArrayList<>();
        Map<String, List<String>> wordMap = null;
        //搜索关键词表
        NetKeywordRelationInfo keywordRelationInfo = searchWordCache(vo.getQ());
        if (keywordRelationInfo != null) {
            //如果关键词类型为C1,类别则再查询一层
            if (keywordRelationInfo.getType().equals(NetKeywordRelationInfo.C1)) {
                relationWordSearch(keywordRelationInfo.getRelationWords());
            }
        } else {
            //不属于关键词,分词逻辑
            keywordRelationInfo = segmentWordDeal(vo.getQ(), words);
        }
        //关键词图片查询
        allKeyword.addAll(wordDealReturnAllKeyword2(keywordRelationInfo, cacheKey, allowCopyRight));
        //经过关联词逻辑,但是没有图片的情况,要走一遍分词
        if (isEmpty(keywordRelationInfo) && keywordRelationInfo.getType() != NetKeywordRelationInfo.C4) {
            keywordRelationInfo = segmentWordDeal(vo.getQ(), words);
            allKeyword.addAll(wordDealReturnAllKeyword2(keywordRelationInfo, cacheKey, allowCopyRight));
        }
        wordMap = generateWordMap(keywordRelationInfo);
        List<OpenEmoticion> openEmoticions = imageGroup(keywordRelationInfo);
        if (openEmoticions == null) {
            openEmoticions = new ArrayList<>();
        }
        if (keywordRelationInfo.getType() != NetKeywordRelationInfo.C4) {
            removeuplicatesForExistKeywords(openEmoticions, words);
        } else {
            topMutliKw(openEmoticions, wordMap);
        }
        return openEmoticions;
    }

    @Override
    public List<OpenEmoticion> onlySearch(String q) {

        List<String> allKeyword = new ArrayList<String>();
        List<OpenEmoticion> emoticions = new ArrayList<OpenEmoticion>();

        String cacheKey = choiceCacheKey("");
        List<String> words = new ArrayList<String>();
        //搜索关键词表
        NetKeywordRelationInfo keywordRelationInfo = searchWordCache(q);
        Map<String, List<String>> wordMap = null;
        if (keywordRelationInfo != null) {
            //如果关键词类型为C1,类别则再查询一层
            if ((keywordRelationInfo.getType() & NetKeywordRelationInfo.C1) > 0) {
                relationWordSearch(keywordRelationInfo.getRelationWords());
            }
        } else {
            //不属于关键词,分词逻辑
            keywordRelationInfo = segmentWordDeal(q, words);
        }
        Boolean allowCopyRight = true;
        allKeyword.addAll(wordDealReturnAllKeyword(keywordRelationInfo, cacheKey, allowCopyRight));
        //经过关联词逻辑,但是没有图片的情况,要走一遍分词
        if (isEmpty(keywordRelationInfo) && keywordRelationInfo.getType() != NetKeywordRelationInfo.C4) {
            keywordRelationInfo = segmentWordDeal(q, words);
            allKeyword.addAll(wordDealReturnAllKeyword(keywordRelationInfo, cacheKey, allowCopyRight));
        }
        wordMap = generateWordMap(keywordRelationInfo);
        emoticions = imageGroup(keywordRelationInfo);
        if (emoticions == null) {
            emoticions = new ArrayList<OpenEmoticion>();
        }
        if (keywordRelationInfo.getType() != NetKeywordRelationInfo.C4) {
            removeuplicates(emoticions);
        } else {
            topMutliKw(emoticions, wordMap);
        }
        if (CollectionUtils.isEmpty(emoticions)) {
            return null;
        }

        return emoticions;
    }


    @Override
    public List<OpenEmoticion> likeSearch(String q) {
        List<BasicNetPictureQuality> qualities = basicNetPictureQualityMapper.searchQualityLikeTextInfo(q, 2);
        if (CollectionUtils.isEmpty(qualities)) {
            return null;
        }
        List<OpenEmoticion> result = new ArrayList<>();
        for (BasicNetPictureQuality quality : qualities) {
            OpenEmoticion openEmoticion = SearchApiUtil.convertNetEmoticionForQuality(quality);
            result.add(openEmoticion);
        }
        return result;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public String resultCache(OpenApiV2ListResponse apiResponseObject, SearchVO vo, boolean sslRes, String appId, boolean allowCopyRight) {
        JsonSerializer jsonSerializer = JsonSerializer.newInstance();
        String s = jsonSerializer.toJson(apiResponseObject);

//        resultCache.hsetCoverTtl(RedisPrefix._OPEN_RESULT_CACHE_V2 + vo.getQ().toLowerCase(), allowCopyRight + "_" + appId + "_" + vo.getFs() + "_" + sslRes + "_" + vo.getP() + "_" + vo.getSize() + "_" + vo.getFs_limit() + "_" + vo.getGt_min() + "_" + vo.getGt_max() + "_" + vo.getGt_crop() + "_" + vo.getEx_fmt() + "_" + vo.getType(), s, 300);
        return s;
    }

    public OpenApiV2ListResponse newGetObjectApiResponseObjectSql(SearchVO vo, boolean sslRes, List<BasicNetPicture> cacheEmoticons, String appId, List<String> keywords) {
//        List<OpenEmoticion> allList = conditionFilter(cacheEmoticons, appId, vo.getFs_limit(), true, vo.getEx_fmt(), vo.getType());
        List<BasicNetPicture> allList = cacheEmoticons;
        int total = allList.size();
//        if (!CollectionUtils.isEmpty(adverts) && !CollectionUtils.isEmpty(allList)) {
//            OpenEmoticionUtils.resultAddAdvert(allList, adverts);
//        }

//        OpenApp app = openAppService.getAppByCache(appId);
//        List<PicturePromotionInfo> promotions = null;
//        if (BooleanUtils.isTrue(app.getPromotionSwitch()) || BooleanUtils.isTrue(app.getGlobalPromotionSwitch())) {
//            promotions = picturePromotionClient.getPicturePromotionAll(appId, AdvertTypeConstant.ADVERT_TYPE_SEARCH, app.getPromotionSwitch(), app.getGlobalPromotionSwitch());
//        }
//        List<EmoticionPromotion> promotionList = null;
//        if (!CollectionUtils.isEmpty(promotions)) {
//            promotionList = plusEmoticionPromotionService.getPromotionByInfo(sslRes, promotions);
//        }

//        if (!CollectionUtils.isEmpty(promotionList)) {
//            allList = OpenEmoticionUtils.addPromotion(allList, promotionList);
//        }


        allList = subList(allList, vo.getSize(), vo.getP());
//        ResultConverUtil.Convert convert = resultConverUtil.ConvertByAppId(sslRes, appId);
//        for (BasicNetPicture o : allList) {
//            try {
//                convert.converURL(o, OpenEmoticionUrlConstant.WEBP_URL,
//                        OpenEmoticionUrlConstant.THUMB_URL, OpenEmoticionUrlConstant.MAIN_URL, OpenEmoticionUrlConstant.GIF_THUMB_URL);
//            } catch (Exception e) {
//                log.warn("URL convert ERROR for OpenEmoticion",e);
//            }
//        }
//        List<OpenEmoticion> responseList = imageMogr(conditionFilterByReturnValue(allList, vo.getEx_fmt()), vo);
        List<BasicNetPicture> responseList = allList;
        OpenApiPagination pagination = responseUtil.generatePagination(total, responseList.size(), PlatformUtils.toIntSafely(vo.getP(),1),  PlatformUtils.toIntSafely(vo.getSize(),20));
        OpenApiV2ListResponse<BasicNetPicture> successOpenApiResponse = responseUtil2.successOpenApiListResponse(responseList,pagination);
        return successOpenApiResponse;
    }

    public OpenApiV2ListResponse newGetObjectApiResponseObject(SearchVO vo, boolean sslRes, List<OpenEmoticion> cacheEmoticons, String appId, List<String> keywords) {
        List<OpenEmoticion> allList = conditionFilter(cacheEmoticons, appId, vo.getFs_limit(), true, vo.getEx_fmt(), vo.getType());
        int total = allList.size();
//        if (!CollectionUtils.isEmpty(adverts) && !CollectionUtils.isEmpty(allList)) {
//            OpenEmoticionUtils.resultAddAdvert(allList, adverts);
//        }

        OpenApp app = openAppService.getAppByCache(appId);
//        List<PicturePromotionInfo> promotions = null;
//        if (BooleanUtils.isTrue(app.getPromotionSwitch()) || BooleanUtils.isTrue(app.getGlobalPromotionSwitch())) {
//            promotions = picturePromotionClient.getPicturePromotionAll(appId, AdvertTypeConstant.ADVERT_TYPE_SEARCH, app.getPromotionSwitch(), app.getGlobalPromotionSwitch());
//        }
//        List<EmoticionPromotion> promotionList = null;
//        if (!CollectionUtils.isEmpty(promotions)) {
//            promotionList = plusEmoticionPromotionService.getPromotionByInfo(sslRes, promotions);
//        }
//
//        if (!CollectionUtils.isEmpty(promotionList)) {
//           allList = OpenEmoticionUtils.addPromotion(allList, promotionList);
//        }


        allList = subList(allList, vo.getSize(), vo.getP());
        ResultConverUtil.Convert convert = resultConverUtil.ConvertByAppId(sslRes, appId);
        for (OpenEmoticion o : allList) {
            try {
                convert.converURL(o, OpenEmoticionUrlConstant.WEBP_URL,
                        OpenEmoticionUrlConstant.THUMB_URL, OpenEmoticionUrlConstant.MAIN_URL, OpenEmoticionUrlConstant.GIF_THUMB_URL);
            } catch (Exception e) {
               log.warn("URL convert ERROR for OpenEmoticion",e);
            }
        }
        List<OpenEmoticion> responseList = imageMogr(conditionFilterByReturnValue(allList, vo.getEx_fmt()), vo);
        OpenApiPagination pagination = responseUtil.generatePagination(total, responseList.size(), PlatformUtils.toIntSafely(vo.getP(),1),  PlatformUtils.toIntSafely(vo.getSize(),20));
        OpenApiV2ListResponse<OpenEmoticion> successOpenApiResponse = responseUtil.successOpenApiListResponse(responseList,pagination);
        return successOpenApiResponse;
    }

    public List<OpenEmoticion> conditionFilter(List<OpenEmoticion> cacheEmoticons, String appId, String fs_limit, Boolean isNew, String fmt, String type) {
        Integer fsLimit = 1048576;
        int staticFsLimit = 102400;
        if (StringUtils.hasText(fs_limit)) {
            fsLimit = PlatformUtils.toIntegerSafely(fs_limit);
            if (fsLimit != null) {
                fsLimit = fsLimit * 1024;
            } else {
                fsLimit = 1048576;
            }
        }
        if (appId.equals(AppConsts.WANGWANG)) {
            fsLimit = 512000;
        }
        Iterator<OpenEmoticion> sListIterator = cacheEmoticons.iterator();
        //临时加文件大小筛选
        while (sListIterator.hasNext()) {
            OpenEmoticion emoticion = sListIterator.next();

            if ((type.equals(SearchTypeConsts.DYNAMIC) && !"1".equals(emoticion.getIsAnimated())) || (type.equals(SearchTypeConsts.STATIC) && !"0".equals(emoticion.getIsAnimated()))) {
                sListIterator.remove();
                continue;
            }
            if ((emoticion.getIsAnimated().equals("1") && emoticion.getFs() > fsLimit) || (emoticion.getIsAnimated().equals("0") && emoticion.getFs() > staticFsLimit)) {
                sListIterator.remove();
                continue;
            }
            //过小权重的图片筛选掉
            if (emoticion.getWeight() < 0 && !isNew) {
                sListIterator.remove();
                continue;
            }
            if (emoticion.getGif_thumb() == null && emoticion.getIsAnimated().equals("1")) {
                sListIterator.remove();
                continue;
            }
            //筛选没有动态缩略图的
            if (emoticion.getIsAnimated().equals("1") && emoticion.getGif_thumb() == null) {
                sListIterator.remove();
                continue;
            }
            //官网渠道确认
            if (!appId.equals(AppConsts.WEB) && emoticion.getMain().contains("netpic/spn")) {
                sListIterator.remove();
                continue;
            }
            if (FmtConsts.WEBP.equals(fmt) && !StringUtils.hasText(emoticion.getWebp())) {
                sListIterator.remove();
            }
        }
        return cacheEmoticons;
    }

    private List<OpenEmoticion> imageMogr(List<OpenEmoticion> list, SearchVO vo) {
        StringBuilder sb = new StringBuilder();
        sb.append("?imageMogr2");
        if (StringUtils.hasText(vo.getGt_max())) {
            sb.append("/thumbnail/");
            sb.append(vo.getGt_max() + "x" + vo.getGt_max());
        } else if (StringUtils.hasText(vo.getGt_min())) {
            sb.append("/thumbnail/");
            sb.append("!" + vo.getGt_min() + "x" + vo.getGt_min() + "r");
        }
        if (StringUtils.hasText(vo.getGt_crop())) {
            sb.append("/gravity/center/crop/");
            sb.append(vo.getGt_crop());
        }
        String imgMogr = sb.toString();
        if (imgMogr.equals("?imageMogr2")) {
            return list;
        }
        Iterator<OpenEmoticion> iterator = list.iterator();
        while (iterator.hasNext()) {
            OpenEmoticion emoticion = iterator.next();
            if (emoticion.getGif_thumb() != null) {
                emoticion.setGif_thumb(emoticion.getGif_thumb() + imgMogr);
            }
        }
        return list;
    }

    public List<OpenEmoticion> conditionFilterByReturnValue(List<OpenEmoticion> listEmoticionConvert, String fmt) {
        for (OpenEmoticion openEmoticion : listEmoticionConvert) {
            String hitKeyword = openEmoticion.getHitKeyword();
            if (StringUtils.hasText(hitKeyword) && hitKeyword.contains("陌陌分类分类词-")) {
                hitKeyword = hitKeyword.substring(8);
                openEmoticion.setHitKeyword(hitKeyword);
            }
            if (!StringUtils.hasText(openEmoticion.getText())) {
                openEmoticion.setText(openEmoticion.getHitKeyword());
            }
            if (!FmtConsts.WEBP.equals(fmt)) {
                openEmoticion.setWebpmd5(null);
                openEmoticion.setWebp(null);
                openEmoticion.setWebpsize(null);
            }
            openEmoticion.setKeywords(null);
            openEmoticion.setHitKeyword(null);
        }
        return listEmoticionConvert;
    }

    private List<OpenEmoticion> getSearchResult(String appId, SearchVO vo, List<String> allKeyword, boolean allowCopyRight) {
        String cacheKey = choiceCacheKey(vo.getFs());
        List<String> words = new ArrayList<String>();
        //搜索关键词表
        NetKeywordRelationInfo keywordRelationInfo = searchWordCache(vo.getQ());
        Map<String, List<String>> wordMap = null;
        if (keywordRelationInfo != null) {
            //如果关键词类型为C1,类别则再查询一层
            if ((keywordRelationInfo.getType() & NetKeywordRelationInfo.C1) > 0) {
                relationWordSearch(keywordRelationInfo.getRelationWords());
            }
        } else {
            //不属于关键词,分词逻辑
            keywordRelationInfo = segmentWordDeal(vo.getQ(), words);
        }
        //关键词图片查询
        allKeyword.addAll(wordDealReturnAllKeyword(keywordRelationInfo, cacheKey, allowCopyRight));
        //经过关联词逻辑,但是没有图片的情况,要走一遍分词
        if (isEmpty(keywordRelationInfo) && keywordRelationInfo.getType() != NetKeywordRelationInfo.C4) {
            keywordRelationInfo = segmentWordDeal(vo.getQ(), words);
            allKeyword.addAll(wordDealReturnAllKeyword(keywordRelationInfo, cacheKey, allowCopyRight));
        }
        wordMap = generateWordMap(keywordRelationInfo);
        if (!AppConsts.MOMO.equals(appId) && !AppConsts.ZUJI.equals(appId) && !AppConsts.DINGDING.equals(appId)) {
            classifyGroupOrderDeal(keywordRelationInfo);
        }
        List<OpenEmoticion> openEmoticions = imageGroup(keywordRelationInfo);
        if (openEmoticions == null) {
            openEmoticions = new ArrayList<OpenEmoticion>();
        }
        if (AppConsts.MOMO.equals(appId) || AppConsts.ZUJI.equals(appId)) {
            openEmoticions = classifyGroupOrder(openEmoticions, keywordRelationInfo.getText(), appId);
        }
        if (keywordRelationInfo.getType() != NetKeywordRelationInfo.C4) {
            removeuplicates(openEmoticions);
        } else {
            topMutliKw(openEmoticions, wordMap);
        }
        for(OpenEmoticion e : openEmoticions){
            e.setFenciWords(words);
        }
        return openEmoticions;
    }

    private Map<String, List<String>> generateWordMap(NetKeywordRelationInfo keywordRelationInfo) {
        Map<String, List<String>> wordMap = new LinkedHashMap<String, List<String>>();
        if (CollectionUtils.isEmpty(keywordRelationInfo.getRelationWords())) {
            return null;
        }
        for (NetKeywordRelationInfo netKeywordRelationInfo : keywordRelationInfo.getRelationWords()) {
            List<String> wordList = new ArrayList<String>();
            if (CollectionUtils.isEmpty(netKeywordRelationInfo.getRelationWords())) {
                wordMap.put(netKeywordRelationInfo.getText(), wordList);
                continue;
            }
            for (NetKeywordRelationInfo relationInfo : netKeywordRelationInfo.getRelationWords()) {
                wordList.add(relationInfo.getText());
            }
            wordMap.put(netKeywordRelationInfo.getText(), wordList);
        }
        return wordMap;
    }

    private List<OpenEmoticion> classifyGroupOrder(List<OpenEmoticion> openEmoticions, String hitKeyword, String appId) {
        List<OpenEmoticion> bmList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> dmList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> zrList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> mcList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> ktList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> otherList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> bmHeadList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> dmHeadList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> zrHeadList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> mcHeadList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> ktHeadList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> otherHeadList = new ArrayList<OpenEmoticion>();
        Iterator<OpenEmoticion> iterator = openEmoticions.iterator();
        while (iterator.hasNext()) {
            OpenEmoticion openEmoticion = iterator.next();
            if (openEmoticion.getWeight() < 0) {
                iterator.remove();
                continue;
            }
            switch (openEmoticion.getClassify()) {
                case 1:
                    if (openEmoticion.getLevel() == 8 && openEmoticion.getHitKeyword().equals(hitKeyword)) {
                        bmHeadList.add(openEmoticion);
                    } else {
                        bmList.add(openEmoticion);
                    }
                    break;
                case 2:
                    //删除静态动漫图
                    if (openEmoticion.getIsAnimated().equals("0")) {
                        iterator.remove();
                        break;
                    }
                    if (openEmoticion.getLevel() == 8 && openEmoticion.getHitKeyword().equals(hitKeyword)) {
                        dmHeadList.add(openEmoticion);
                    } else {
                        dmList.add(openEmoticion);
                    }
                    break;
                case 5:
                    if (openEmoticion.getLevel() == 8 && openEmoticion.getHitKeyword().equals(hitKeyword)) {
                        zrHeadList.add(openEmoticion);
                    } else {
                        zrList.add(openEmoticion);
                    }
                    break;
                case 4:
                    if (openEmoticion.getLevel() == 8 && openEmoticion.getHitKeyword().equals(hitKeyword)) {
                        mcHeadList.add(openEmoticion);
                    } else {
                        mcList.add(openEmoticion);
                    }
                    break;
                case 8:
                    if (openEmoticion.getLevel() == 8 && openEmoticion.getHitKeyword().equals(hitKeyword)) {
                        ktHeadList.add(openEmoticion);
                    } else {
                        ktList.add(openEmoticion);
                    }
                    break;
                default:
                    if (openEmoticion.getLevel() == 8 && openEmoticion.getHitKeyword().equals(hitKeyword)) {
                        otherHeadList.add(openEmoticion);
                    } else {
                        otherList.add(openEmoticion);
                    }
                    break;
            }
        }
        bmList.addAll(0, bmHeadList);
        dmList.addAll(0, dmHeadList);
        zrList.addAll(0, zrHeadList);
        mcList.addAll(0, mcHeadList);
        ktList.addAll(0, ktHeadList);
        otherList.addAll(0, otherHeadList);
        List<OpenEmoticion> resultList = new ArrayList<OpenEmoticion>();
        boolean isHeadDeal = false;
        if (AppConsts.ZUJI.equals(appId)) {
            int size = bmList.size() + ktList.size() + dmList.size();
            while (true) {
                if (!CollectionUtils.isEmpty(bmList)) {
                    resultList.addAll(subAndDeleteList(bmList, 5));
                }
                if (!CollectionUtils.isEmpty(ktList)) {
                    resultList.addAll(subAndDeleteList(ktList, 5));
                }
                if (!CollectionUtils.isEmpty(dmList)) {
                    resultList.addAll(subAndDeleteList(dmList, 5));
                }
                if (resultList.size() == size) {
                    break;
                }
            }
        } else {
            while (true) {
                if (!CollectionUtils.isEmpty(zrList)) {
                    resultList.addAll(subAndDeleteList(zrList, 2));
                }
                if (!CollectionUtils.isEmpty(ktList)) {
                    resultList.addAll(subAndDeleteList(ktList, 2));
                }
                if (!CollectionUtils.isEmpty(dmList)) {
                    resultList.addAll(subAndDeleteList(dmList, 2));
                }
                if (!CollectionUtils.isEmpty(mcList)) {
                    resultList.addAll(subAndDeleteList(mcList, 2));
                }
                if (!CollectionUtils.isEmpty(bmList)) {
                    resultList.addAll(subAndDeleteList(bmList, 2));
                }
                if (CollectionUtils.isEmpty(bmList) && CollectionUtils.isEmpty(dmList) && CollectionUtils.isEmpty(zrList) && CollectionUtils.isEmpty(mcList) && CollectionUtils.isEmpty(ktList)) {
                    resultList.addAll(otherList);
                    break;
                }
            }
        }
        return resultList;
    }

    private void removeuplicates(List<OpenEmoticion> resultList) {
        List<OpenEmoticion> newList = new LinkedList<OpenEmoticion>();
        HashSet<OpenEmoticion> set = new HashSet<OpenEmoticion>();
        for (OpenEmoticion openEmoticion : resultList) {
            if (set.add(openEmoticion)) {
                List<String> keywords = new ArrayList<String>();
                keywords.add(openEmoticion.getHitKeyword());
                openEmoticion.setKeywords(keywords);
                newList.add(openEmoticion);
            } else {
                int index = newList.indexOf(openEmoticion);
                OpenEmoticion hasEmoticion = newList.get(index);
                List<String> keywords = hasEmoticion.getKeywords();
                keywords.add(openEmoticion.getHitKeyword());
            }
        }
        resultList.clear();
        resultList.addAll(newList);
    }

    private void classifyGroupOrderDeal(NetKeywordRelationInfo keywordRelationInfo) {
        if (keywordRelationInfo != null) {
            if (!CollectionUtils.isEmpty(keywordRelationInfo.getOpenEmoticions())) {
                keywordRelationInfo.setOpenEmoticions(defaultClassifyGroupOrder(keywordRelationInfo.getOpenEmoticions()));
            }
            if (CollectionUtils.isEmpty(keywordRelationInfo.getRelationWords())) {
                return;
            } else {
                for (NetKeywordRelationInfo netKeywordRelationInfo : keywordRelationInfo.getRelationWords()) {
                    classifyGroupOrderDeal(netKeywordRelationInfo);
                }
            }
        }
    }

    private void topMutliKw(List<OpenEmoticion> resultList, Map<String, List<String>> wordMap) {
        if (wordMap == null) {
            return;
        }
        List<OpenEmoticion> newList = new LinkedList<OpenEmoticion>();
        HashSet<OpenEmoticion> set = new HashSet<OpenEmoticion>();
        List<OpenEmoticion> mutliKwList = new ArrayList<OpenEmoticion>();
        for (OpenEmoticion openEmoticion : resultList) {
            if (set.add(openEmoticion)) {
                List<String> keywords = new ArrayList<String>();
                keywords.add(openEmoticion.getHitKeyword());
                openEmoticion.setKeywords(keywords);
                newList.add(openEmoticion);
            } else {
                int mutliListIndex = mutliKwList.indexOf(openEmoticion);
                if (mutliListIndex < 0) {
                    mutliKwList.add(openEmoticion);
                }
            }
        }
        for (OpenEmoticion openEmoticion : mutliKwList) {
            List<String> allKeywords = openEmoticion.getAllKeywords();
            if (CollectionUtils.isEmpty(allKeywords)) {
                openEmoticion.setWeight(0);
                continue;
            }
            List<Integer> levelList = new ArrayList<Integer>();
            int i = 0;
            for (Map.Entry<String, List<String>> entry : wordMap.entrySet()) {
                if (allKeywords.contains(entry.getKey())) {
                    //分词关联词结构map， 如果图片关键词包含分词关键词则优先级为2，如果图片关键词只包含分词关联词优先级为1
                    //第一个完全符合的优先级最高为3
                    if (i == 0) {
                        levelList.add(3);
                    } else {
                        levelList.add(2);
                    }
                    continue;
                }
                for (String keyword : entry.getValue()) {
                    if (allKeywords.contains(keyword)) {
                        levelList.add(1);
                        break;
                    }
                }
                i++;
            }
            int mutliKwScore = 1;
            for (Integer level : levelList) {
                mutliKwScore += level;
            }
            mutliKwScore = mutliKwScore + (100 * levelList.size());
            openEmoticion.setWeight(mutliKwScore);
        }
        descOrderWeightSort(mutliKwList);
        set = new HashSet<OpenEmoticion>(mutliKwList);
        for (OpenEmoticion openEmoticion : newList) {
            if (!set.contains(openEmoticion)) {
                mutliKwList.add(openEmoticion);
            }
        }
        resultList.clear();
        resultList.addAll(mutliKwList);
    }

    private void descOrderWeightSort(List<OpenEmoticion> mutliKwList) {
        Collections.sort(mutliKwList, new Comparator<OpenEmoticion>() {
            @Override
            public int compare(OpenEmoticion o1, OpenEmoticion o2) {
                if (o1.getWeight() < o2.getWeight()) {
                    return 1;
                }
                if (o1.getWeight() == o2.getWeight()) {
                    return 0;
                }
                return -1;
            }
        });
    }

    private List<OpenEmoticion> defaultClassifyGroupOrder(List<OpenEmoticion> openEmoticions) {
        List<OpenEmoticion> bmList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> dmList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> ktList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> zrList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> mcList = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> otherList = new ArrayList<OpenEmoticion>();
        Iterator<OpenEmoticion> iterator = openEmoticions.iterator();
        while (iterator.hasNext()) {
            OpenEmoticion openEmoticion = iterator.next();
            switch (openEmoticion.getClassify()) {
                case 1:
                    bmList.add(openEmoticion);
                    break;
                case 2:
                    dmList.add(openEmoticion);
                    break;
                case 5:
                    zrList.add(openEmoticion);
                    break;
                case 4:
                    mcList.add(openEmoticion);
                    break;
                case 8:
                    ktList.add(openEmoticion);
                    break;
                default:
                    otherList.add(openEmoticion);
                    break;
            }
        }
        List<OpenEmoticion> resultList = new ArrayList<OpenEmoticion>();
        while (true) {
            if (!CollectionUtils.isEmpty(zrList)) {
                resultList.addAll(subAndDeleteList(zrList, 5));
            }
            if (!CollectionUtils.isEmpty(mcList)) {
                resultList.addAll(subAndDeleteList(mcList, 4));
            }
            if (!CollectionUtils.isEmpty(bmList)) {
                resultList.addAll(subAndDeleteList(bmList, 2));
            }
            if (!CollectionUtils.isEmpty(ktList)) {
                resultList.addAll(subAndDeleteList(ktList, 2));
            }
            if (!CollectionUtils.isEmpty(dmList)) {
                resultList.addAll(subAndDeleteList(dmList, 2));
            }
            if (!CollectionUtils.isEmpty(otherList)) {
                resultList.addAll(subAndDeleteList(otherList, 2));
            }
            if (CollectionUtils.isEmpty(bmList) && CollectionUtils.isEmpty(dmList) && CollectionUtils.isEmpty(zrList) && CollectionUtils.isEmpty(mcList) && CollectionUtils.isEmpty(ktList)) {
                resultList.addAll(otherList);
                break;
            }
        }
        return resultList;
    }

    private List<String> wordDealReturnAllKeyword(NetKeywordRelationInfo keywordRelationInfo, String cacheKey, boolean allowCopyRight) {
        List<String> allKeyword = new ArrayList<String>();
        collectAllKeyword(keywordRelationInfo, allKeyword);
        String[] keywordArray = new String[allKeyword.size()];
        for (int i = 0; i < allKeyword.size(); i++) {
            keywordArray[i] = allKeyword.get(i);
        }
        if (keywordArray.length > 0) {
            List<List<OpenEmoticion>> emoticionsList = (List<List<OpenEmoticion>>) platformCache.hmget(cacheKey, keywordArray);
            collectAllKeyword2(keywordRelationInfo, allKeyword, emoticionsList, allowCopyRight);
        }
        return allKeyword;
    }

    private NetKeywordRelationInfo segmentWordDeal(String q, List<String> words) {
        NetKeywordRelationInfo keywordRelationInfo = new NetKeywordRelationInfo(q, NetKeywordRelationInfo.C4);
        String str = getDivision(q);
        List<String> segments = WordUtils.segment(str);
        if (segments.size() > 5) {
            words.addAll(new ArrayList<String>(segments.subList(0, 5)));
        } else {
            words.addAll(segments);
        }
        List<NetKeywordRelationInfo> relationInfos = relationWordSearchForSegmentWord(words);
        keywordRelationInfo.setRelationWords(relationInfos);
        System.out.println("分词结果是："+ words);
        return keywordRelationInfo;
    }

    private String getDivision(String str) {
        String divisionReg = "(.)\\1{3,}";
        return str.replaceAll(divisionReg, "$1$1$1");
    }

    private List<NetKeywordRelationInfo> relationWordSearchForSegmentWord(List<String> segmentWords) {
        List<NetKeywordRelationInfo> keywordRelationInfos = new ArrayList<NetKeywordRelationInfo>();
        if (CollectionUtils.isEmpty(segmentWords)) {
            return keywordRelationInfos;
        }
        Pattern pattern = Pattern.compile("[A-Za-z]+");
        for (String segmentWord : segmentWords) {
            NetKeywordRelationInfo keywordRelationInfo = searchWordCache(segmentWord);
            if (keywordRelationInfo != null) {
                keywordRelationInfo.setRelationWeight(10);
                keywordRelationInfos.add(keywordRelationInfo);
            } else {
                //拼音逻辑
                Matcher matcher = pattern.matcher(segmentWord);
                if (matcher.find()) {
                    String[] pinyinWords = (String[]) platformCache.hget(RedisPrefix._OPEN_PINYIN_TAG, segmentWord.toLowerCase());
                    if (pinyinWords != null && pinyinWords.length > 0) {
                        for (String pinyinWord : pinyinWords) {
                            NetKeywordRelationInfo pinyinKeywordRelationInfo = searchWordCache(pinyinWord);
                            if (pinyinKeywordRelationInfo != null) {
                                pinyinKeywordRelationInfo.setRelationWeight(10);
                                pinyinKeywordRelationInfo.setRelationWords(null);
                                keywordRelationInfos.add(pinyinKeywordRelationInfo);
                            }
                        }
                    }
                }
            }
        }
        return keywordRelationInfos;
    }

    private List<NetKeywordRelationInfo> relationWordSearch(List<NetKeywordRelationInfo> relationWords) {
        if (CollectionUtils.isEmpty(relationWords)) {
            return relationWords;
        }
        for (NetKeywordRelationInfo relationWord : relationWords) {
            NetKeywordRelationInfo keywordRelationInfo = searchWordCache(relationWord.getText());
            if (keywordRelationInfo != null)
                relationWord.setRelationWords(keywordRelationInfo.getRelationWords());
        }
        return relationWords;
    }



    public String choiceCacheKey(String fs) {
        if (fs.equals("large")) {
            return RedisPrefix._OPEN_SEARCH_LARGE_TAG;
        } else if (fs.equals("medium")) {
            return RedisPrefix._OPEN_SEARCH_MEDIUM_TAG;
        } else if (fs.equals("small")) {
            return RedisPrefix._OPEN_SEARCH_SMALL_TAG;
        } else {
            return RedisPrefix._OPEN_SEARCH_MEDIUM_TAG;
        }
    }

    private NetKeywordRelationInfo searchWordCache(String word) {
        NetKeywordRelationInfo keywordRelationInfo = (NetKeywordRelationInfo) platformCache.hget(RedisPrefix._OPEN_NEW_KEYWORD_TAG, word.toLowerCase());
        return keywordRelationInfo;
    }

    private List<String> wordDealReturnAllKeyword2(NetKeywordRelationInfo keywordRelationInfo, String cacheKey, boolean allowCopyRight) {
        List<String> allKeyword = new ArrayList<String>();
        collectAllKeyword(keywordRelationInfo, allKeyword);
        String[] keywordArray = new String[allKeyword.size()];
        for (int i = 0; i < allKeyword.size(); i++) {
            keywordArray[i] = allKeyword.get(i);
        }
        if (keywordArray.length > 0) {
            List<List<OpenEmoticion>> emoticionsList = (List<List<OpenEmoticion>>) platformCache.hmget(RedisPrefix._OPEN_SEARCH_DENSION_TAG, keywordArray);
            collectAllKeyword2(keywordRelationInfo, allKeyword, emoticionsList, allowCopyRight);
        }
        return allKeyword;
    }

    private List<OpenEmoticion> imageGroup(NetKeywordRelationInfo keywordRelationInfo) {
        List<Integer> group_range = new ArrayList<Integer>() {{
            add(10);
            add(4);
            add(3);
            add(2);
            add(1);
        }};
        List<NetKeywordRelationInfo> relationWords = keywordRelationInfo.getRelationWords();
        if (CollectionUtils.isEmpty(relationWords)) {
            if (!CollectionUtils.isEmpty(keywordRelationInfo.getOpenEmoticions())) {
                for (OpenEmoticion openEmoticion : keywordRelationInfo.getOpenEmoticions()) {
                    if (CollectionUtils.isEmpty(openEmoticion.getKeywords())) {
                        List<String> keywords = new ArrayList<String>();
                        keywords.add(openEmoticion.getHitKeyword());
                        openEmoticion.setKeywords(keywords);
                    }
                }
            }
            return keywordRelationInfo.getOpenEmoticions();
        }
        LinkedList<OpenEmoticion> resultList = new LinkedList<OpenEmoticion>();
        NetKeywordRelationInfo directKeywordInfo = new NetKeywordRelationInfo(keywordRelationInfo.getText(), keywordRelationInfo.getType());
        directKeywordInfo.setOpenEmoticions(keywordRelationInfo.getOpenEmoticions());
        if (directKeywordInfo.getType() == NetKeywordRelationInfo.C1) {
            relationWords.add(directKeywordInfo);
        } else {
            relationWords.add(0, directKeywordInfo);
        }
        keywordRelationInfo.setOpenEmoticions(null);
        while (!isEmpty(keywordRelationInfo)) {
            Iterator<NetKeywordRelationInfo> iterator = relationWords.iterator();
            while (iterator.hasNext()) {
                NetKeywordRelationInfo relationWord = iterator.next();
                if (group_range.size() == 0) {
                    if (relationWord.equals(directKeywordInfo)) {
                        group_fetch(resultList, relationWord, 15);
                    } else {
                        group_fetch(resultList, relationWord, 5);
                    }
                } else {
                    group_fetch(resultList, relationWord, group_range.get(0));
                    group_range.remove(0);
                }
                if (isEmpty(relationWord)) {
                    iterator.remove();
                    continue;
                }
            }
        }
        return resultList;
    }

    private void removeuplicatesForExistKeywords(List<OpenEmoticion> resultList, List<String> segmentList) {
        List<OpenEmoticion> newList = new LinkedList<OpenEmoticion>();
        HashSet<OpenEmoticion> set = new HashSet<OpenEmoticion>();
        for (OpenEmoticion openEmoticion : resultList) {
            if (set.add(openEmoticion)) {
                newList.add(openEmoticion);
            } else {
                int index = newList.indexOf(openEmoticion);
                OpenEmoticion hasEmoticion = newList.get(index);
                if (segmentList.contains(hasEmoticion.getHitKeyword()) && segmentList.contains(openEmoticion.getHitKeyword())) {
                    newList.remove(openEmoticion);
                    if (!openEmoticion.getKeywords().contains(hasEmoticion.getHitKeyword())) {
                        openEmoticion.getKeywords().add(hasEmoticion.getHitKeyword());
                    }
                    newList.add(0, openEmoticion);
                } else if (!segmentList.contains(hasEmoticion.getHitKeyword()) && segmentList.contains(openEmoticion.getHitKeyword())) {
                    hasEmoticion.setHitKeyword(openEmoticion.getHitKeyword());
                }
            }
        }
        resultList.clear();
        resultList.addAll(newList);
    }

    private void group_fetch(LinkedList<OpenEmoticion> resultList, NetKeywordRelationInfo relationWord, Integer range) {
        int directKeywordCount = (int) Math.ceil(range / 2.0);
        if (isEmpty(relationWord)) {
            return;
        }
        List<NetKeywordRelationInfo> relationWords = relationWord.getRelationWords();
        if (!CollectionUtils.isEmpty(relationWord.getOpenEmoticions())) {
            if (CollectionUtils.isEmpty(relationWords)) {
                List<OpenEmoticion> openEmoticions = subAndDeleteList(relationWord.getOpenEmoticions(), range);
                resultList.addAll(openEmoticions);
                return;
            }
            List<OpenEmoticion> openEmoticions = subAndDeleteList(relationWord.getOpenEmoticions(), directKeywordCount);
            directKeywordCount = openEmoticions.size();
            resultList.addAll(openEmoticions);
        }
        int idlCount = range - directKeywordCount;
        if (idlCount <= 0) {
            return;
        }
        if (!CollectionUtils.isEmpty(relationWords)) {
            Iterator<NetKeywordRelationInfo> iterator = relationWords.iterator();
            while (iterator.hasNext()) {
                NetKeywordRelationInfo netKeywordRelationInfo = iterator.next();
                if (isEmpty(netKeywordRelationInfo)) {
                    iterator.remove();
                } else {
                    group_fetch(resultList, netKeywordRelationInfo, idlCount);
                    return;
                }
            }
        }
    }

    public boolean isEmpty(NetKeywordRelationInfo keywordRelationInfo) {
        if (CollectionUtils.isEmpty(keywordRelationInfo.getOpenEmoticions()) && CollectionUtils.isEmpty(keywordRelationInfo.getRelationWords())) {
            return true;
        }
        if (!CollectionUtils.isEmpty(keywordRelationInfo.getOpenEmoticions())) {
            return false;
        }
        for (NetKeywordRelationInfo netKeywordRelationInfo : keywordRelationInfo.getRelationWords()) {
            if (!isEmpty(netKeywordRelationInfo)) {
                return false;
            }
        }
        return true;
    }

    private List<OpenEmoticion> subAndDeleteList(List<OpenEmoticion> openEmoticions, int size) {
        List<OpenEmoticion> subEmoticions = null;
        if (openEmoticions.size() > size) {
            subEmoticions = new ArrayList<OpenEmoticion>(openEmoticions.subList(0, size));
            //此处不能使用removeAll,因为removeAll会将size外所有重复项全都删除
            for (int i = 0; i < size; i++) {
                openEmoticions.remove(0);
            }
            return subEmoticions;
        }
        subEmoticions = new ArrayList<OpenEmoticion>(openEmoticions.subList(0, openEmoticions.size()));
        if (subEmoticions.size() > 0) {
            openEmoticions.clear();
        }
        return subEmoticions;
    }

    private void collectAllKeyword2(NetKeywordRelationInfo keywordRelationInfo, List<String> allKeyword, List<List<OpenEmoticion>> emoticionsList, boolean allowCopyRight) {
        if (keywordRelationInfo != null) {
            if (keywordRelationInfo.getType() != NetKeywordRelationInfo.C4) {
                int i = allKeyword.indexOf(keywordRelationInfo.getText());
                List<OpenEmoticion> emoticions = emoticionsList.get(i);
                if (!allowCopyRight) {
                    removeCopyRightEmoji(emoticions);
                }
                keywordRelationInfo.setOpenEmoticions(emoticions);
            }
            if (CollectionUtils.isEmpty(keywordRelationInfo.getRelationWords())) {
                return;
            } else {
                for (NetKeywordRelationInfo netKeywordRelationInfo : keywordRelationInfo.getRelationWords()) {
                    collectAllKeyword2(netKeywordRelationInfo, allKeyword, emoticionsList, allowCopyRight);
                }
            }
        }
    }

    private void removeCopyRightEmoji(List<OpenEmoticion> cacheEmoticons) {
        if (CollectionUtils.isEmpty(cacheEmoticons)) {
            return;
        }
        Iterator<OpenEmoticion> iterator = cacheEmoticons.iterator();
        while (iterator.hasNext()) {
            OpenEmoticion emoticion = iterator.next();
            if (emoticion.getMain().contains("/emoticonPacket/")) {
                iterator.remove();
            }
        }
    }

    private void collectAllKeyword(NetKeywordRelationInfo keywordRelationInfo, List<String> words) {
        if (keywordRelationInfo != null) {
            if (keywordRelationInfo.getType() != NetKeywordRelationInfo.C4) {
                words.add(keywordRelationInfo.getText());
            }
            if (CollectionUtils.isEmpty(keywordRelationInfo.getRelationWords())) {
                return;
            } else {
                for (NetKeywordRelationInfo netKeywordRelationInfo : keywordRelationInfo.getRelationWords()) {
                    collectAllKeyword(netKeywordRelationInfo, words);
                }
            }
        }
    }

    public String returnEmpty() {
        String resultStr = "{\"data\":[],\"pagination\":{\"count\":0,\"total_count\":0,\"offset\":0},\"status\":0,\"msg\":\"\"}";
        return resultStr;
    }

    public boolean isTaobaoWhitelist(String query, String appId) {
        if (!appId.equals(AppConsts.WANGWANG)) {
            return false;
        }
        return !taobaoWhitelistUtil.isContains(query);
    }

}
