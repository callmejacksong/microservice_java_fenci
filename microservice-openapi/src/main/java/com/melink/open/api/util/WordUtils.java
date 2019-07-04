package com.melink.open.api.util;

import com.melink.microservice.cache.PlatformCache;
import com.melink.sop.api.models.RedisPrefix;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.dictionary.DictionaryFactory;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.apdplat.word.segmentation.WordRefiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orlan on 2017/1/18.
 */
@Component("wordUtils")
public class WordUtils {
    @Autowired
    private PlatformCache<String, Object> platformCache;

    private final Logger log = LoggerFactory.getLogger(WordUtils.class);

    public static List<String> segment(String word) {
        List<String> segList = seg(word, SegmentationAlgorithm.BidirectionalMaximumMatching);
        return segList;
    }

    private static List<String> seg(String text, SegmentationAlgorithm algorithm) {
        List<String> segStr = new ArrayList<String>();
        List<Word> words;
        words = WordSegmenter.seg(text, algorithm);
        words = WordRefiner.refine(words);
        for (Word word : words) {
            segStr.add(word.getText());
        }
        return segStr;
    }

    public void generateWordTxt(){
        log.warn("开始加载词库");
        List<String> allStopWord = (List<String>) platformCache.hget(RedisPrefix._OPEN_CORE_KEYWORD_CACHE,"stopwords");
        List<String> allCoreKeyword = (List<String>) platformCache.hget(RedisPrefix._OPEN_CORE_KEYWORD_CACHE,"corewords");
        if(allCoreKeyword == null || allCoreKeyword == null){
            return;
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("/home/admin/word/cdic.txt"));
            for (String coreKeyword : allCoreKeyword) {
                writer.write(coreKeyword);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("/home/admin/word/stopwords.txt"));
            for (String coreKeyword : allStopWord) {
                writer.write(coreKeyword);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        allCoreKeyword = null;
        allStopWord = null;
        DictionaryFactory.reload();
        log.warn("词库加载结束");
    }
}
