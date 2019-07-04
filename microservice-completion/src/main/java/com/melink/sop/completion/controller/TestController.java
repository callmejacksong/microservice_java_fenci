package com.melink.sop.completion.controller;

import com.melink.microservice.cache.PlatformCache;
import com.melink.sop.completion.search.common.RestResult;
import com.melink.sop.completion.search.common.SearchClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Author     ：liyang.
 * @ Date       ：Created in 14:32 2018/12/17
 * @ Description：test controller
 * @ Modified By：
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private PlatformCache<String, Object> platformCache;

    @Autowired
    private SearchClient searchClient;


    @RequestMapping(value = "/es", method = RequestMethod.GET)
    public RestResult<List<String>> es() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("secom");
        searchRequest.types("words");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        CompletionSuggestionBuilder completionSuggestionBuilder = SuggestBuilders.completionSuggestion("word.testsearch");
        completionSuggestionBuilder.skipDuplicates(true);
        completionSuggestionBuilder.size(100);
        completionSuggestionBuilder.prefix("哈");
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("word-suggest", completionSuggestionBuilder);
        searchSourceBuilder.suggest(suggestBuilder);
        searchRequest.source(searchSourceBuilder);
        return RestResult.getSuccessResult(searchClient.suggest(searchRequest, "word-suggest"));
    }

}
