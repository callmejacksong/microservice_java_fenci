package com.melink.open.api.service.impl;

import com.melink.microservice.cache.PlatformCache;
import com.melink.microservice.utils.ResultConverUtil;
import com.melink.open.api.constant.AppConsts;
import com.melink.open.api.mapper.netPicMapper.BasicNetPictureQualityMapper;
import com.melink.open.api.mapper.netPicMapper.IpAccountMapper;
import com.melink.open.api.mapper.netPicMapper.NetKeywordWeightMapper;
import com.melink.open.api.model.BasicNetPictureQuality;
import com.melink.open.api.model.IpAccount;
import com.melink.open.api.model.IpAccountActivity;
import com.melink.open.api.model.NetKeywordWeight;
import com.melink.open.api.service.HotNetkeyWordService;
import com.melink.open.api.util.HttpsResConvertUtils;
import com.melink.sop.api.models.QiNiuBucket;
import com.melink.sop.api.models.RedisPrefix;
import com.melink.sop.api.models.open.modelinfos.HotNetKeywordInfo;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.melink.open.api.util.SearchApiUtil.convertNetEmoticionForQuality;


@Service
public class HotNetkeyWordServiceImpl implements HotNetkeyWordService {

    @Autowired
    private PlatformCache<String, Object> platformCache;

    @Autowired
    private NetKeywordWeightMapper netKeywordWeightMapper;

    @Autowired
    private BasicNetPictureQualityMapper basicNetPictureQualityMapper;

    @Autowired
    private IpAccountMapper ipAccountMapper;

    @Autowired
    private ResultConverUtil resultConverUtil;

    @Override
    public List<HotNetKeywordInfo> hot(String timestamp, String signature, String appId, boolean sslRes, boolean mutliEmo) {
        List<HotNetKeywordInfo> keywordInfoList = null;
        if(AppConsts.DONG2.equals(appId)) {
            if (!mutliEmo) {
                keywordInfoList = (List<HotNetKeywordInfo>) platformCache.getObject(RedisPrefix._OPEN_NETWORD_HOT_HTTPS_TAG);
                if (CollectionUtils.isEmpty(keywordInfoList)) {
                    List<NetKeywordWeight> hotWords = netKeywordWeightMapper.hotWords();
                    keywordInfoList = covertHotKeywordInfoList(hotWords, true,appId);
                    platformCache.putByte(RedisPrefix._OPEN_NETWORD_HOT_HTTPS_TAG, keywordInfoList);
                }
            } else {
                keywordInfoList = (List<HotNetKeywordInfo>) platformCache.getObject(RedisPrefix._OPEN_NETWORD_HOT_MUTLI_TAG);
                if (CollectionUtils.isEmpty(keywordInfoList)) {
                    List<NetKeywordWeight> hotWords = netKeywordWeightMapper.hotWords();
                    keywordInfoList = covertHotKeywordInfoMutliEmoList(hotWords, true);
                    platformCache.putByte(RedisPrefix._OPEN_NETWORD_HOT_MUTLI_TAG, keywordInfoList);
                }
            }
        }else{
            if(sslRes) {
                keywordInfoList = (List<HotNetKeywordInfo>) platformCache.getObject(RedisPrefix._OPEN_NETWORD_SOSO_HTTPS_HOT_TAG);
                if (CollectionUtils.isEmpty(keywordInfoList)) {
                    List<NetKeywordWeight> hotWords = netKeywordWeightMapper.hotWords();
                    keywordInfoList = covertHotKeywordInfoList(hotWords, sslRes,appId);
                    platformCache.putByte(RedisPrefix._OPEN_NETWORD_SOSO_HTTPS_HOT_TAG, keywordInfoList);
                }
            }else{
                keywordInfoList = (List<HotNetKeywordInfo>) platformCache.getObject(RedisPrefix._OPEN_NETWORD_SOSO_HOT_TAG);
                if (CollectionUtils.isEmpty(keywordInfoList)) {
                    List<NetKeywordWeight> hotWords = netKeywordWeightMapper.hotWords();
                    keywordInfoList = covertHotKeywordInfoList(hotWords, sslRes,appId);
                    platformCache.putByte(RedisPrefix._OPEN_NETWORD_SOSO_HOT_TAG, keywordInfoList);
                }
            }
        }
        return keywordInfoList;

    }

    public List<HotNetKeywordInfo> covertHotKeywordInfoMutliEmoList(List<NetKeywordWeight> words,boolean sslRes) {
        List<HotNetKeywordInfo> keywordInfoList = new ArrayList<HotNetKeywordInfo>();
        for (NetKeywordWeight keywordWeight : words) {
            List<OpenEmoticion> emoticions = null;
            HotNetKeywordInfo keywordInfo = new HotNetKeywordInfo();
            keywordInfo.setText(keywordWeight.getKeyword().getText());
            IpAccount ipAccount = findByName(keywordWeight.getKeyword().getText());
            if(ipAccount != null){
                emoticions = new ArrayList<OpenEmoticion>();
                List<BasicNetPictureQuality> basicNetPictureQualities = basicNetPictureQualityMapper.searchNetPictureQualityForIpAccount(ipAccount.getId(), 1, 6);
                for (BasicNetPictureQuality basicNetPictureQuality : basicNetPictureQualities) {
                    OpenEmoticion emoticion = convertNetEmoticionForQuality(basicNetPictureQuality,keywordWeight.getKeyword().getText());
                    emoticions.add(emoticion);
                }
            }else{
                emoticions = (List<OpenEmoticion>) platformCache.hget(RedisPrefix._OPEN_SEARCH_DENSION_TAG, keywordWeight.getKeyword().getText().toLowerCase());
            }

            if(!CollectionUtils.isEmpty(emoticions)) {
                emoticions = HttpsResConvertUtils.listEmoticionConvert(subList(emoticions, 0, 5), sslRes);
                keywordInfo.setEmoticions(emoticions);
            }
            keywordInfoList.add(keywordInfo);
        }
        return keywordInfoList;
    }

    public List<HotNetKeywordInfo> covertHotKeywordInfoList(List<NetKeywordWeight> words,boolean sslRes,String appId) {
        List<HotNetKeywordInfo> keywordInfoList = new ArrayList<HotNetKeywordInfo>();
        for (NetKeywordWeight keywordWeight : words) {
            List<OpenEmoticion> emoticions = (List<OpenEmoticion>) platformCache.hget(RedisPrefix._OPEN_SEARCH_DENSION_TAG, keywordWeight.getKeyword().getText().toLowerCase());
            if(!CollectionUtils.isEmpty(emoticions)) {
                emoticions = HttpsResConvertUtils.listEmoticionConvert(subList(emoticions, 0, 1), sslRes);
                for (OpenEmoticion emoticion : emoticions) {
                    BasicNetPictureQuality basicNetPictureQuality = basicNetPictureQualityMapper.findByNpid(emoticion.getGuid(), 2);
                    HotNetKeywordInfo keywordInfo = new HotNetKeywordInfo();
                    String coverImg = emoticion.getMain().replace(sslRes ? QiNiuBucket.BQMM_SOSO_HTTPS_BUCKET:QiNiuBucket.BQMM_SOSO_BUCKET,"");
                    if(basicNetPictureQuality != null){
                        coverImg = basicNetPictureQuality.getStoreUrl();
                    }
                    keywordInfo.setCover(coverImg);
                    keywordInfo.setHeight(emoticion.getHeight());
                    keywordInfo.setWidth(emoticion.getWidth());
                    keywordInfo.setFs(emoticion.getFs());
                    keywordInfo.setText(keywordWeight.getKeyword().getText());
                    ResultConverUtil.Convert convert = resultConverUtil.ConvertByAppId(sslRes, appId);
                    try {
                        convert.converURL(keywordInfo,"cover");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    keywordInfoList.add(keywordInfo);
                }
            }
        }
        return keywordInfoList;
    }

    public List<OpenEmoticion> subList(List<OpenEmoticion> list, int startIndex, int endIndex) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        }
        Iterator<OpenEmoticion> sListIterator = list.iterator();
        //临时加文件大小筛选
        while (sListIterator.hasNext()) {
            OpenEmoticion emoticion = sListIterator.next();
            if (emoticion.getMain().contains("/emoticonPacket/")){
                sListIterator.remove();
            }
        }
        if (endIndex < startIndex || startIndex >= list.size()) {
            return new ArrayList();
        }
        if (endIndex > list.size()) {
            endIndex = list.size();
        }
        return new ArrayList<OpenEmoticion>(list.subList(startIndex, endIndex));
    }

    /**
     *
     * @param name ipAccount 中的name
     * @return account得到Id查询activity如果有给属性setIpActivity=true
     *             为null返回null
     */
    public IpAccount findByName(String name) {
        IpAccount account = ipAccountMapper.findByName(name);
        if (account != null) {
            List<IpAccountActivity> activitys = ipAccountMapper.findActivityByIpId(account.getId());
            if (!CollectionUtils.isEmpty(activitys)) {
                account.setIpActivity(true);
            }
        }
        return account;
    }

//    public List<NetKeywordWeight> hotWords() {
//        List<NetKeywordWeight> netKeywordWeights = netKeywordWeightMapper.hotWords();
//        List<String> ids = netKeywordWeights.stream().map(NetKeywordWeight::getKeywordId).collect(Collectors.toList());
//        List<NetKeyword> keywordByKeywordIds = keywordNetMapper.findKeywordByKeywordIds(ids);
//        Map<String, NetKeyword> netkeywordMap = keywordByKeywordIds.stream().collect(Collectors.toMap(NetKeyword::getGuid, NetKeyword -> NetKeyword));
//        List<NetKeywordWeight> result = netKeywordWeights.stream().map(s -> {
//            NetKeyword netKeyword = netkeywordMap.get(s.getKeywordId());
//            s.setKeyword(netKeyword);
//            return s;
//        }).collect(Collectors.toList());
//        return result;
//    }
}
