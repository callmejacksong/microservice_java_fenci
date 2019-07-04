package com.melink.open.api.service.impl;

import com.melink.microservice.cache.PlatformCache;
import com.melink.microservice.json.JsonSerializer;
import com.melink.microservice.utils.DateUtils;
import com.melink.microservice.utils.HolidayUtils;
import com.melink.open.api.constant.AppConsts;
import com.melink.open.api.constant.PeriodConsts;
import com.melink.open.api.feignClient.AdvertClient;
import com.melink.open.api.mapper.netPicMapper.TrendyEmoticonMapper;
import com.melink.open.api.model.TrendyEmoticon;
import com.melink.open.api.service.OpenAppService;
import com.melink.open.api.service.TrendingService;
import com.melink.open.api.util.OpenEmoticionUtils;
import com.melink.open.api.vo.ValidaterVO;
import com.melink.sop.api.constant.advertConstant.AdvertTypeConstant;
import com.melink.sop.api.models.RedisPrefix;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.util.*;

import static com.melink.open.api.util.SearchApiUtil.convert;


@Service
public class TrendingServiceImpl implements TrendingService {

    @Autowired
    private OpenAppService openAppService;

    @Autowired
    private PlatformCache<String, Object> platformCache;

    @Autowired
    private TrendyEmoticonMapper trendyEmoticonMapper;
    //调用广告服务接口的api
    @Autowired
    private AdvertClient advertClient;

    private static final int TIME_TRENDING_COUNT = 5;
    private static final int FIRST_PAGE_TRENDING_COUNT = 20;

    public List<OpenEmoticion> newTrendy(String timestamp, String signature, String appId, boolean sslRes, @Valid ValidaterVO vo) {

        //获取广告数据 adverts
        List<OpenEmoticion> adverts = advertClient.getAdvertByType(AdvertTypeConstant.ADVERT_TYPE_TRENDING);

        try {
            String trendCacheKey = "";
            trendCacheKey = RedisPrefix._OPEN_EMPTICON_NEW_TRENDING_TAG + "-" + appId;

            if (openAppService.isTestApp(appId)) {
                return getTestAppTrendy(adverts);
            }

            int rangeType = choiceTimeRange(appId);
            Map<String, Object> cacheMap = (Map<String, Object>) platformCache.getObject(trendCacheKey);
            if (cacheMap != null) {
                if (rangeType == (Integer) cacheMap.get("type")) {
                    List<OpenEmoticion> openEmoticions = (List<OpenEmoticion>) cacheMap.get("data");
                    //如果有广告，添加广告图片
                    if (!CollectionUtils.isEmpty(adverts) && !CollectionUtils.isEmpty(openEmoticions)) {
                        OpenEmoticionUtils.resultAddAdvert(openEmoticions, adverts);
                    }
                    return openEmoticions;
                }
            }
            List<OpenEmoticion> openEmoticions = getTimeTypeResultEmoticions(rangeType, appId);
            cacheMap = new HashMap<String, Object>();
            cacheMap.put("type", rangeType);
            if (openEmoticions.size() > 100) {
                openEmoticions = new ArrayList<OpenEmoticion>(openEmoticions.subList(0, 100));
            }
            cacheMap.put("data", openEmoticions);
            platformCache.putByte(trendCacheKey, cacheMap, 7200);
            //如果有广告，添加广告图片
            if (!CollectionUtils.isEmpty(adverts) && !CollectionUtils.isEmpty(openEmoticions)) {
                OpenEmoticionUtils.resultAddAdvert(openEmoticions, adverts);
            }
            return openEmoticions;
        } catch (Exception e) {
            List<OpenEmoticion> cacheList = getTrendingForCache(appId);
            //如果有广告，添加广告图片
            if (!CollectionUtils.isEmpty(adverts) && !CollectionUtils.isEmpty(cacheList)) {
                OpenEmoticionUtils.resultAddAdvert(cacheList, adverts);
            }
            return cacheList;
        }

    }

    @Override
    public List<OpenEmoticion> textInfoTrending(boolean hasTextinfo) {
        String trendCacheKey = null;

        if (hasTextinfo) {
            trendCacheKey = RedisPrefix._OPEN_EMPTICON_TEXTINFO_TRENDING_TAG;
            List<OpenEmoticion> openemoticion = getOpenemoticionHasTextInfo(trendCacheKey, hasTextinfo);
            return openemoticion;
        } else {
            trendCacheKey = RedisPrefix._OPEN_EMPTICON_NOT_TEXTINFO_TRENDING_TAG;

            List<OpenEmoticion> openemoticion = getOpenemoticionHasTextInfo(trendCacheKey, hasTextinfo);
            return openemoticion;
        }
    }


    private List<OpenEmoticion> getTestAppTrendy(List<OpenEmoticion> adverts) {
        List<OpenEmoticion> trendList = (List<OpenEmoticion>) platformCache.getObject(RedisPrefix._OPEN_TRENDING_TEST_TOKEN);
        if (trendList == null) {
            trendList = new ArrayList<OpenEmoticion>();
        }
        //如果有广告，添加广告图片
        if (!CollectionUtils.isEmpty(adverts) && !CollectionUtils.isEmpty(trendList)) {
            OpenEmoticionUtils.resultAddAdvert(trendList, adverts);
        }
        return trendList;
    }


//-----------------------------------------------------------------------------------------------------------------------------------------------


    private List<OpenEmoticion> getTrendingForCache(String appId) {
        List<OpenEmoticion> cacheList = (List<OpenEmoticion>) platformCache.getObject(RedisPrefix._OPEN_EMPTICON_TRENDING_TAG + "-" + appId);
        if (!CollectionUtils.isEmpty(cacheList)) {
            return cacheList;
        }
        //查询数据库中appid = appid and show_date is null
        List<TrendyEmoticon> trendyEmoticons = trendyEmoticonMapper.findAllTrendingByAppId(appId);
        List<OpenEmoticion> openEmoticions = new ArrayList<OpenEmoticion>();
        if (!CollectionUtils.isEmpty(trendyEmoticons)) {
            for (TrendyEmoticon trendyEmoticon : trendyEmoticons) {
                OpenEmoticion openEmoticion = convert(trendyEmoticon);
                openEmoticions.add(openEmoticion);
            }
            platformCache.putByte(RedisPrefix._OPEN_EMPTICON_TRENDING_TAG + "-" + appId, openEmoticions, -1);
            return openEmoticions;
        }
        cacheList = (List<OpenEmoticion>) platformCache.getObject(RedisPrefix._OPEN_EMPTICON_TRENDING_TAG);
        if (!CollectionUtils.isEmpty(cacheList)) {
            return cacheList;
        }
        trendyEmoticons = trendyEmoticonMapper.findAllTrendingAppIdIsNullAndShowDaetIsNull();
        for (TrendyEmoticon trendyEmoticon : trendyEmoticons) {
            OpenEmoticion openEmoticion = convert(trendyEmoticon);
            openEmoticions.add(openEmoticion);
        }
        platformCache.putByte(RedisPrefix._OPEN_EMPTICON_TRENDING_TAG, openEmoticions);
        return openEmoticions;
    }

    private int choiceTimeRange(String appId) {
        Calendar c = Calendar.getInstance();
        List<String> hoildayList = HolidayUtils.getHoildayList();
        String date = DateUtils.getDate(c.getTime());
        if (hoildayList.contains(date)) {
            return 0;
        }
        if (!AppConsts.TANTAN.equals(appId)) {
            int i = c.get(Calendar.HOUR_OF_DAY);
            if (i >= 4 && i < 10) {
                return 1;
            } else if (i >= 12 && i < 14) {
                return 2;
            } else if (i >= 18 && i < 20) {
                return 3;
            } else if (i >= 22 || i < 4) {
                return 4;
            } else {
                return 0;
            }
        } else {
            int i = c.get(Calendar.HOUR_OF_DAY);
            if (i >= 4 && i < 10) {
                return 1;
            } else if (i >= 11 && i < 13) {
                return 2;
            } else if (i >= 13 && i < 18) {
                return 3;
            } else if (i >= 18 || i < 20) {
                return 4;
            } else if (i >= 20 || i < 4) {
                return 5;
            } else {
                return 0;
            }
        }
    }

    public List<OpenEmoticion> getTimeTypeResultEmoticions(int rangeType, String appId) {
        List<OpenEmoticion> openEmoticions = getTrendingForCache(appId);
        List<OpenEmoticion> timeEmoticions = new ArrayList<OpenEmoticion>();
        List<OpenEmoticion> dateEmoticons = getDateEmoticons(appId);
        if (rangeType > 0) {
            timeEmoticions = getTimeRangeEmojis(rangeType, appId);
            if (timeEmoticions.size() > TIME_TRENDING_COUNT) {
                timeEmoticions = new ArrayList<OpenEmoticion>(timeEmoticions.subList(0, TIME_TRENDING_COUNT));
            }
        }
        openEmoticions.addAll(0, dateEmoticons);
        openEmoticions = trendingRandomList(openEmoticions, timeEmoticions);
        return openEmoticions;
    }

    private List<OpenEmoticion> getDateEmoticons(String appId) {
        if (appId == AppConsts.WANGWANG) {
            return new ArrayList<OpenEmoticion>();
        }
        List<TrendyEmoticon> trendyEmoticons = trendyEmoticonMapper.findTodayTrendyEmoticonsByAppId(appId, trendyEmoticonMapper.findCountByAppId(appId));
        if (CollectionUtils.isEmpty(trendyEmoticons)) {
            return new ArrayList<OpenEmoticion>();
        }
        List<OpenEmoticion> openEmoticions = new ArrayList<OpenEmoticion>();
        for (TrendyEmoticon trendyEmoticon : trendyEmoticons) {
            OpenEmoticion openEmoticion = convert(trendyEmoticon);
            openEmoticions.add(openEmoticion);
        }
        return openEmoticions;
    }

    private List<OpenEmoticion> trendingRandomList(List<OpenEmoticion> list, List<OpenEmoticion> timeList) {
        int remainingTrendIngCount = FIRST_PAGE_TRENDING_COUNT - timeList.size();
        List<OpenEmoticion> trendingList = null;
        int randomSize = 60;
        if (list.size() < 60) {
            randomSize = list.size();
        }
        trendingList = new ArrayList<OpenEmoticion>(list.subList(0, randomSize));
        list = new ArrayList<OpenEmoticion>(list.subList(randomSize, list.size()));
        List<OpenEmoticion> lockList = new ArrayList<OpenEmoticion>();
        Iterator<OpenEmoticion> iterator = trendingList.iterator();
        while (iterator.hasNext()) {
            OpenEmoticion emoticion = iterator.next();
            if (emoticion.getTrendLock() > 0) {
                lockList.add(emoticion);
                iterator.remove();
            }
        }
        Collections.shuffle(trendingList);
        List<OpenEmoticion> openEmoticions = null;
        if (remainingTrendIngCount > lockList.size()) {
            remainingTrendIngCount = remainingTrendIngCount - lockList.size();
            if (remainingTrendIngCount > trendingList.size()) {
                remainingTrendIngCount = trendingList.size();
            }
            openEmoticions = new ArrayList<OpenEmoticion>(trendingList.subList(0, remainingTrendIngCount));
            openEmoticions.addAll(timeList);
            openEmoticions.addAll(lockList);
            Collections.shuffle(openEmoticions);
            openEmoticions.addAll(new ArrayList<OpenEmoticion>(trendingList.subList(remainingTrendIngCount, trendingList.size())));
            openEmoticions.addAll(list);
        } else {
            if (remainingTrendIngCount > lockList.size()) {
                remainingTrendIngCount = lockList.size();
            }
            openEmoticions = new ArrayList<OpenEmoticion>(lockList.subList(0, remainingTrendIngCount));
            openEmoticions.addAll(timeList);
            Collections.shuffle(openEmoticions);
            openEmoticions.addAll(new ArrayList<OpenEmoticion>(lockList.subList(remainingTrendIngCount, lockList.size())));
            openEmoticions.addAll(trendingList);
            openEmoticions.addAll(list);
        }

        return openEmoticions;
    }

    private List<OpenEmoticion> getTimeRangeEmojis(int rangeType, String appId) {
        String typeStr = PeriodConsts.dataMap.get(rangeType);
        if (AppConsts.TANTAN.equals(appId)) {
            typeStr = PeriodConsts.tantanMap.get(rangeType);
        } else if (AppConsts.WANGWANG.equals(appId)) {
            return new ArrayList<OpenEmoticion>();
        } else if (AppConsts.ZUJI.equals(appId)) {
            return new ArrayList<OpenEmoticion>();
        }
        List<OpenEmoticion> emoticons = (List<OpenEmoticion>) platformCache.hget(RedisPrefix._OPEN_SEARCH_MEDIUM_TAG, typeStr.toLowerCase());
        return emoticons;
    }

    private List<OpenEmoticion> getOpenemoticionHasTextInfo(String trendCacheKey, Boolean hasTextInfo) {
        Object cacheMap = platformCache.get(trendCacheKey, String.class);

        if (cacheMap != null) {
            List<OpenEmoticion> openEmoticions = JsonSerializer.json2list(cacheMap.toString(), OpenEmoticion.class);
            return openEmoticions;
        }
        List<TrendyEmoticon> trendyTextInfo = null;
        if (hasTextInfo) {
            trendyTextInfo = trendyEmoticonMapper.findAllTrendyByTextInfo();
        } else {
            trendyTextInfo = trendyEmoticonMapper.findAllTrendyNotTextInfo();
        }
        List<OpenEmoticion> openEmoticions = new ArrayList<OpenEmoticion>();
        if (!CollectionUtils.isEmpty(trendyTextInfo)) {
            for (TrendyEmoticon trendyEmoticon : trendyTextInfo) {
                OpenEmoticion openEmoticion = convert(trendyEmoticon);
                openEmoticions.add(openEmoticion);
            }
            platformCache.put(trendCacheKey, openEmoticions, 2 * 60 * 60);

        }
        return openEmoticions;
    }
}
