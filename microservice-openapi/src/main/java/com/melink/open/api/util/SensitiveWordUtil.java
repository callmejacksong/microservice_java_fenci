package com.melink.open.api.util;

import com.melink.microservice.cache.PlatformCache;
import com.melink.open.api.constant.AppConsts;
import com.melink.open.api.model.SensitiveWord;
import com.melink.sop.api.models.RedisPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by orlan on 2016/12/16.
 */
@Component("sensitiveWordUtil")
public class SensitiveWordUtil {
    private static Set<String> sensiviceWordList = new HashSet<String>();
    private static Set<String> igonreWordList = new HashSet<String>();
    private static Set<String> momoSensiviceWordList = new HashSet<String>();
    private static Set<String> qqZoneSensiviceWordList = new HashSet<String>();
    private static Set<String> whiteList = new HashSet<String>();
    private static String patternStr = "";
    private static String momoPatternStr = "";
    private static Pattern pattern = null;
    private static Pattern momoPattern = null;

    private static Map wordMap = null;
    private static Map momoWordMap = null;
    private static Map qqZoneWordMap = null;
    // 最小匹配规则
    public static int minMatchTYpe = 1;

    // 最大匹配规则
    public static int maxMatchType = 2;

    @Autowired
//    @Qualifier("RedisCache")
    private PlatformCache<String, Object> platformCache;

    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveWordUtil.class);

    public SensitiveWordUtil() {
    }

    public void init() {
        LOGGER.warn("-----开始加载敏感词库-----");
        Set<String> tempSnsiviceWordList = new HashSet<String>();
        Set<String> tempIgonreWordList = new HashSet<String>();
        Set<String> tempMomoSensiviceWordList = new HashSet<String>();
        Set<String> tempQqZoneSensiviceWordList = new HashSet<String>();
        String tempPatternStr = "";
        String tempMomoPatternStr = "";
        Pattern tempPattern = null;
        Pattern tempMomoPattern = null;
        Map temoWordMap = null;
        Map tempMomoWordMap = null;
        Map tempQqZoneWordMap = null;
        List<SensitiveWord> defaultWords = (List<SensitiveWord>) platformCache.getObject(RedisPrefix._OPEN_SENSITIVE_WORD_TAG);
        List<SensitiveWord> momoWords = (List<SensitiveWord>) platformCache.getObject(RedisPrefix._OPEN_SENSITIVE_WORD_TAG + "-1247d6b11ae949779e7a0c85f0cef4af");
        List<SensitiveWord> qqZoneWords = (List<SensitiveWord>) platformCache.getObject(RedisPrefix._OPEN_SENSITIVE_WORD_TAG + "-ec176e29e1cc46e6a0847986d95c5979");
        for (SensitiveWord defaultWord : defaultWords) {
            switch (defaultWord.getType()) {
                case 0:
                    tempSnsiviceWordList.add(defaultWord.getText());
                    break;
                case 1:
                    tempIgonreWordList.add(defaultWord.getText());
                    break;
                case 2:
                    tempPatternStr += defaultWord.getText() + "|";
                    break;
                case 3:
                    whiteList.add(defaultWord.getText().toLowerCase());
                    break;
            }
        }
        for (SensitiveWord momoWord : momoWords) {
            switch (momoWord.getType()) {
                case 0:
                    tempMomoSensiviceWordList.add(momoWord.getText());
                    break;
                case 2:
                    tempMomoPatternStr += momoWord.getText() + "|";
                    break;
            }
        }
        if (!CollectionUtils.isEmpty(qqZoneWords)) {
            for (SensitiveWord qqZoneWord : qqZoneWords) {
                switch (qqZoneWord.getType()) {
                    case 0:
                        tempQqZoneSensiviceWordList.add(qqZoneWord.getText());
                        break;
                }
            }
        }
        if (StringUtils.hasText(tempPatternStr)) {
            tempPatternStr = tempPatternStr.substring(0, tempPatternStr.length() - 1);
        }
        if (StringUtils.hasText(tempMomoPatternStr)) {
            tempMomoPatternStr = tempMomoPatternStr.substring(0, tempMomoPatternStr.length() - 1);
        }
        tempPattern = Pattern.compile(tempPatternStr);
        tempMomoPattern = Pattern.compile(tempMomoPatternStr);
        temoWordMap = addSensitiveWordToHashMap(tempSnsiviceWordList);
        tempMomoWordMap = addSensitiveWordToHashMap(tempMomoSensiviceWordList);
        tempQqZoneWordMap = addSensitiveWordToHashMap(tempQqZoneSensiviceWordList);

        sensiviceWordList = tempSnsiviceWordList;
        momoSensiviceWordList = tempMomoSensiviceWordList;
        qqZoneSensiviceWordList = tempQqZoneSensiviceWordList;
        igonreWordList = tempIgonreWordList;
        pattern = tempPattern;
        momoPattern = tempMomoPattern;
        wordMap = temoWordMap;
        momoWordMap = tempMomoWordMap;
        qqZoneWordMap = tempQqZoneWordMap;
        LOGGER.warn("-----加载敏感词库完成-----");
    }

    private Map addSensitiveWordToHashMap(Set<String> wordSet) {

        // 初始化敏感词容器，减少扩容操作
        Map wordMap = new HashMap(wordSet.size());

        for (String word : wordSet) {
            Map nowMap = wordMap;
            for (int i = 0; i < word.length(); i++) {

                // 转换成char型
                char keyChar = word.charAt(i);

                // 获取
                Object tempMap = nowMap.get(keyChar);

                // 如果存在该key，直接赋值
                if (tempMap != null) {
                    nowMap = (Map) tempMap;
                }

                // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                else {

                    // 设置标志位
                    Map<String, String> newMap = new HashMap<String, String>();
                    newMap.put("isEnd", "0");

                    // 添加到集合
                    nowMap.put(keyChar, newMap);
                    nowMap = newMap;
                }

                // 最后一个
                if (i == word.length() - 1) {
                    nowMap.put("isEnd", "1");
                }
            }
        }

        return wordMap;
    }

    private boolean isContaintSensitiveWord(String txt, Map tempMap) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {

            // 判断是否包含敏感字符
            int matchFlag = this.CheckSensitiveWord(txt, i, 1, tempMap);

            // 大于0存在，返回true
            if (matchFlag > 0) {
                flag = true;
            }
        }
        return flag;
    }

    private int CheckSensitiveWord(String txt, int beginIndex, int matchType, Map tempMap) {

        // 敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;

        // 匹配标识数默认为0
        int matchFlag = 0;
        Map nowMap = tempMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            char word = txt.charAt(i);

            // 获取指定key
            nowMap = (Map) nowMap.get(word);

            // 存在，则判断是否为最后一个
            if (nowMap != null) {

                // 找到相应key，匹配标识+1
                matchFlag++;

                // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))) {

                    // 结束标志位为true
                    flag = true;

                    // 最小规则，直接返回,最大规则还需继续查找
                    if (minMatchTYpe == matchType) {
                        break;
                    }
                }
            }

            // 不存在，直接返回
            else {
                break;
            }
        }

        // 长度必须大于等于1，为词
        if (matchFlag < 1 || !flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }

    public boolean isContains(String word, String appId) {
        word = word.toLowerCase();
        if (whiteList.contains(word)) {
            return false;
        }
        if (igonreWordList.contains(word)) {
            return true;
        }
        if (appId.equals(AppConsts.MOMO)) {
            Matcher matcher = momoPattern.matcher(word);
            if (matcher.matches()) {
                return true;
            }
            if (isContaintSensitiveWord(word, momoWordMap)) {
                return true;
            }
        }
        if (appId.equals(AppConsts.QQZONE)) {
            if (isContaintSensitiveWord(word, qqZoneWordMap)) {
                return true;
            }
        }
        Matcher matcher = pattern.matcher(word);
        if (matcher.matches()) {
            return true;
        }
        if (appId.equals(AppConsts.WEB) || appId.equals(AppConsts.ALIPAY)) {
            if (word.contains("蘑菇头") || word.contains("蚊子动漫")) {
                return true;
            }
        }
        return isContaintSensitiveWord(word, wordMap);
    }
}
