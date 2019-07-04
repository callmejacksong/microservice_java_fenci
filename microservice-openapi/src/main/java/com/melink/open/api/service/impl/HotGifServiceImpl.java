package com.melink.open.api.service.impl;

import com.melink.microservice.cache.PlatformCache;
import com.melink.microservice.utils.Tools;
import com.melink.open.api.mapper.netPicMapper.BasicNetPictureQualityMapper;
import com.melink.open.api.mapper.netPicMapper.NetKeywordWeightMapper;
import com.melink.open.api.model.BasicNetPictureQuality;
import com.melink.open.api.model.NetKeywordWeight;
import com.melink.open.api.service.HotGifService;
import com.melink.sop.api.models.RedisPrefix;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.melink.open.api.util.SearchApiUtil.convertNetEmoticionForQuality;

@Service
public class HotGifServiceImpl implements HotGifService {
    @Autowired
    private PlatformCache<String, Object> platformCache;
    @Autowired
    private BasicNetPictureQualityMapper basicNetPictureQualityMapper;
    @Autowired
    private NetKeywordWeightMapper netKeywordWeightMapper;


    @Override
    public List<OpenEmoticion> getHotOpenEmoticion(String p, String size) {
        List<OpenEmoticion> hotGifs = (List<OpenEmoticion>) platformCache.getObject(RedisPrefix._OPEN_HOT_GIF_TAG);
        //List<OpenEmoticion> hotGifs = null;
        if (CollectionUtils.isEmpty(hotGifs)) {
            List<BasicNetPictureQuality> basicNetPictureQualityList = basicNetPictureQualityMapper.searchNewestNetPicture(1);
            List<List<OpenEmoticion>> mergeList = new ArrayList<>();
            List<OpenEmoticion> newestList = new ArrayList<>();
            for (BasicNetPictureQuality basicEmoticon : basicNetPictureQualityList) {
                OpenEmoticion emoticion = convertNetEmoticionForQuality(basicEmoticon);
                newestList.add(emoticion);
            }
            List<NetKeywordWeight> hotTagWords = netKeywordWeightMapper.hotTagWords();
            for (NetKeywordWeight hotTagWord : hotTagWords) {
                List<OpenEmoticion> cacheEmoticons = (List<OpenEmoticion>) platformCache.hget(RedisPrefix._OPEN_SEARCH_DENSION_TAG, hotTagWord.getKeyword().getText());
                mergeList.add(Tools.subListNoPage(cacheEmoticons,0,100));
            }
            Set<OpenEmoticion> resultList = new HashSet<>();
            for (List<OpenEmoticion> openEmoticions : mergeList) {
                resultList.addAll(openEmoticions);
            }
            hotGifs = new ArrayList<>(resultList);

            //前一百张
            Collections.shuffle(newestList);
            Set<OpenEmoticion> headSet = new HashSet<>();
            headSet.addAll(Tools.subListNoPage(newestList,0,30));
            headSet.addAll(Tools.subListNoPage(hotGifs,0,70));
            List<OpenEmoticion> headList = new ArrayList<>(headSet);
            Collections.shuffle(headList);

            //合并新传图和热门图去重并随机
            resultList.clear();
            resultList.addAll(newestList);
            resultList.addAll(hotGifs);
            hotGifs.clear();
            hotGifs = new ArrayList<>(resultList);
            Collections.shuffle(hotGifs);

            //合并前一百张和所有图去重
            for (OpenEmoticion emoticion : headList) {
                if(resultList.add(emoticion)){
                    hotGifs.add(0,emoticion);
                }
            }

            Iterator<OpenEmoticion> iterator = hotGifs.iterator();
            while (iterator.hasNext()){
                OpenEmoticion next = iterator.next();
                if(!StringUtils.hasText(next.getGif_thumb())){
                    iterator.remove();
                }
            }
            platformCache.putByte(RedisPrefix._OPEN_HOT_GIF_TAG,hotGifs,7200);
        }
        return hotGifs;
    }

//    public List<NetKeywordWeight> hotTagWords() {
//        List<NetKeywordWeight> netKeywordWeights = netKeywordWeightMapper.hotTagWords();
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
