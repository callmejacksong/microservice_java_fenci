package com.melink.open.api.service.impl;

import com.melink.open.api.mapper.netPicMapper.NetKeywordWeightMapper;
import com.melink.open.api.model.NetKeywordWeight;
import com.melink.open.api.service.WebSiteCategoryService;
import com.melink.sop.api.models.open.modelinfos.WebsiteCategoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebSiteCategoryServiceImpl implements WebSiteCategoryService {
    @Autowired
    private NetKeywordWeightMapper netKeywordWeightMapper;

    @Override
    public List<WebsiteCategoryInfo> getWebsiteCategoryInfo() {
        List<NetKeywordWeight> categoryWords = netKeywordWeightMapper.categoryWords();
        List<WebsiteCategoryInfo> categoryList = new ArrayList<>();
        for (NetKeywordWeight categoryWord : categoryWords) {
            WebsiteCategoryInfo websiteCategoryInfo = new WebsiteCategoryInfo();
            websiteCategoryInfo.setText(categoryWord.getKeyword().getText());
            websiteCategoryInfo.setBackground(categoryWord.getBackground());
            categoryList.add(websiteCategoryInfo);

        }
        return categoryList;
    }

//    List<NetKeywordWeight> categoryWords() {
//        List<NetKeywordWeight> netKeywordWeights = netKeywordWeightMapper.categoryWords();
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
