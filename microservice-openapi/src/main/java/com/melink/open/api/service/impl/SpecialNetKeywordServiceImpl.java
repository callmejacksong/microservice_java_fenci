package com.melink.open.api.service.impl;

import com.melink.open.api.mapper.netPicMapper.NetKeywordWeightMapper;
import com.melink.open.api.model.NetKeywordWeight;
import com.melink.open.api.service.SpecialNetKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SpecialNetKeywordServiceImpl implements SpecialNetKeywordService {
    @Autowired
    private NetKeywordWeightMapper netKeywordWeightMapper;
    @Override
    public List<String> getKeywordList() {
        List<NetKeywordWeight> commonWords = netKeywordWeightMapper.commonWords();
        List<String> infos = new ArrayList<>();
        for (NetKeywordWeight keywordWeight : commonWords) {
            infos.add(keywordWeight.getKeyword().getText());
        }
        return infos;
    }

//    List<NetKeywordWeight> commonWords() {
//        List<NetKeywordWeight> netKeywordWeights = netKeywordWeightMapper.commonWords();
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
