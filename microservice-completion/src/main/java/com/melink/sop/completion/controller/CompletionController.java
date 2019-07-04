package com.melink.sop.completion.controller;

import com.melink.sop.completion.search.common.RestResult;
import com.melink.sop.completion.service.CompletionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：liyang.
 * @ Date       ：Created in 14:32 2018/12/17
 * @ Description：test controller
 * @ Modified By：
 */
@RestController
@RequestMapping(value = "/completion")
public class CompletionController {

    @Autowired
    private CompletionService completionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompletionController.class);

    @RequestMapping("")
    public RestResult<List<String>> es(@RequestParam("key") String word) {
        LOGGER.warn("suggest begin..........");
        List<String> charList = new ArrayList<String>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c >= 19968 && c <= 171941) {
                charList.add(String.valueOf(c));
            }
        }
        List<String> completion = completionService.completion(word);
        String lowerWord = word.toLowerCase();
        List<String> completionWords = new ArrayList<String>();
        for (String item : completion) {
            if (item.toLowerCase().startsWith(lowerWord)) {
                completionWords.add(item);
            }
            if (completionWords.size() >= 6) {
                return RestResult.getSuccessResult(completionWords);
            }
        }

        for (String item : completion) {
            boolean qualified = true;
            if (completionWords.contains(item)) {
                continue;
            }
            for (String c : charList) {
                if (!item.contains(c)) {
                    qualified = false;
                    break;
                }
            }
            if (qualified) {
                completionWords.add(item);
            }

            if (completionWords.size() >= 6) {
                break;
            }

        }

        return RestResult.getSuccessResult(completionWords);
    }

}
