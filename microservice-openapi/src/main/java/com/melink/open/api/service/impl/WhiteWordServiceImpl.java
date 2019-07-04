package com.melink.open.api.service.impl;

import com.melink.microservice.cache.PlatformCache;
import com.melink.microservice.utils.Tools;
import com.melink.open.api.mapper.netPicMapper.SensitiveWordMapper;
import com.melink.open.api.mapper.netPicMapper.WhitelistWordRecordMapper;
import com.melink.open.api.model.SensitiveWord;
import com.melink.open.api.model.WhitelistWordRecord;
import com.melink.open.api.service.WhiteWordService;
import com.melink.sop.api.models.RedisPrefix;
import com.melink.sop.api.models.open.modelinfos.WhiteListInfo;
import com.melink.sop.api.models.open.modelinfos.WhiteListItemInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WhiteWordServiceImpl implements WhiteWordService {

    @Autowired
    private PlatformCache<String, Object> platformCache;

    @Autowired
    private WhitelistWordRecordMapper whitelistWordRecordMapper;

    @Autowired
    private SensitiveWordMapper sensitiveWordMapper;


    @Override
    public WhiteListInfo whitelistCheck(String version) {
        WhiteListInfo whiteListInfo = new WhiteListInfo();
        //获取缓存中最新版本号，如果相同直接返回版本信息
        String cacheVersion = (String) platformCache.get(RedisPrefix._OPEN_WHITELIST_VERSION, String.class);
        if (cacheVersion == null) {
            cacheVersion = whitelistWordRecordMapper.findMaxVersion();
            platformCache.put(RedisPrefix._OPEN_WHITELIST_VERSION, cacheVersion);
        }
        whiteListInfo.setVersion(cacheVersion);

        if (cacheVersion != null && version != null && cacheVersion.equals(version)) {
            List<WhiteListItemInfo> whiteListItemInfos = new ArrayList<WhiteListItemInfo>();
            whiteListInfo.setList(whiteListItemInfos);
            return whiteListInfo;
        }

        //如果是第一次获取则返回有所的数据
        if (version == null || version.equals("0")) {
            List<WhiteListItemInfo> whiteListItemInfos = resetCurrentVersionWhitelist();
            whiteListInfo.setList(whiteListItemInfos);
            return whiteListInfo;
        }

        if(!StringUtils.isNumeric(version) || Tools.strIntCompare(version,cacheVersion) > 0){
            whiteListInfo.setReset(true);
            List<WhiteListItemInfo> whiteListItemInfos = resetCurrentVersionWhitelist();
            whiteListInfo.setList(whiteListItemInfos);
            return whiteListInfo;
        }

        List<WhitelistWordRecord> whitelistWordRecords = whitelistWordRecordMapper.findVersion(Integer.parseInt(version));
        List<WhiteListItemInfo> whiteListItemInfos = new ArrayList<WhiteListItemInfo>();
        for (WhitelistWordRecord whitelistWordRecord : whitelistWordRecords) {
            WhiteListItemInfo item = new WhiteListItemInfo();
            item.setText(whitelistWordRecord.getText());
            item.setType(whitelistWordRecord.getType());
            whiteListItemInfos.add(item);
        }
        whiteListInfo.setList(whiteListItemInfos);
        return whiteListInfo;

    }

    private List<WhiteListItemInfo> resetCurrentVersionWhitelist() {
        List<WhiteListItemInfo> whiteListItemInfos = new ArrayList<>();
        List<SensitiveWord> sensitiveWordList = sensitiveWordMapper.findWhitelistWord();
        for (SensitiveWord sensitiveWord : sensitiveWordList) {
            WhiteListItemInfo item = new WhiteListItemInfo();
            item.setText(sensitiveWord.getText());
            item.setType(0);
            whiteListItemInfos.add(item);
        }
        return whiteListItemInfos;
    }
}
