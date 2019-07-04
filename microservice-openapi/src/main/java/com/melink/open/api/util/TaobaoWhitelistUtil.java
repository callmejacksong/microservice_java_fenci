package com.melink.open.api.util;

import com.melink.microservice.cache.PlatformCache;
import com.melink.open.api.model.SensitiveWord;
import com.melink.sop.api.models.RedisPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("taobaoWhitelistUtil")
public class TaobaoWhitelistUtil {
    private static Map wordMap = null;
    private static Set<String> wordList = new HashSet<String>();
    // 最小匹配规则
    public static int minMatchTYpe = 1;

    @Autowired
    private PlatformCache<String, Object> platformCache;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaobaoWhitelistUtil.class);

    public TaobaoWhitelistUtil() {
    }

    public void init(){
        LOGGER.warn("-----开始加载淘宝白名单词库-----");
        Set<String> tempList = new HashSet<String>();
        List<SensitiveWord> sensitiveWords = (List<SensitiveWord>) platformCache.getObject(RedisPrefix._OPEN_WANGWANG_WHITELIST);
        if(!CollectionUtils.isEmpty(sensitiveWords)){
            for (SensitiveWord sensitiveWord : sensitiveWords) {
                tempList.add(sensitiveWord.getText());
            }
            wordList = tempList;
        }
        LOGGER.warn("-----淘宝白名单词库完成-----");
    }


    public boolean isContains(String word) {
        return wordList.contains(word);
    }
}
