package com.melink.sop.completion.service.impl;

import com.melink.sop.completion.search.common.SearchClient;
import com.melink.sop.completion.service.CompletionService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @ Author     ：liyang.
 * @ Date       ：Created in 15:39 2018/12/20
 * @ Description：
 * @ Modified By：
 */
@Service
public class CompletionServiceImpl implements CompletionService {

    @Autowired
    private SearchClient searchClient;

    private static String index = "secom";
    private static String type = "words";
    private static String suggestName = "word-suggest";
    private static Integer size = 100;

    @Override
    public List<String> completion(String word) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.types(type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        CompletionSuggestionBuilder completionSuggestionBuilder = SuggestBuilders.completionSuggestion("word.testsearch");
        completionSuggestionBuilder.skipDuplicates(true);
        completionSuggestionBuilder.size(size);
        completionSuggestionBuilder.prefix(word);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion(suggestName, completionSuggestionBuilder);
        searchSourceBuilder.suggest(suggestBuilder);
        searchRequest.source(searchSourceBuilder);
        List<String> suggestList = searchClient.suggest(searchRequest, suggestName);
        return suggestList;
    }
}
