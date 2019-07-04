package com.melink.sop.completion.search.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("searchClient")
@Slf4j
public class EsSearchClient implements SearchClient {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public List<JSONObject> search(SearchRequest request) {
        try {
            SearchResponse response = client.search(request);
            if (response.getHits() == null) {
                return null;
            }
            List<JSONObject> list = new ArrayList<>();
            response.getHits().forEach(item -> list.add(JSON.parseObject(item.getSourceAsString())));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> search(SearchRequest request, Class<T> tClass) {
        List<JSONObject> searchResponse = this.search(request);
        if (searchResponse == null) {
            return null;
        }
        List<T> list = new ArrayList<>(searchResponse.size());
        searchResponse.forEach(item -> list.add(JSON.parseObject(JSON.toJSONString(item), tClass)));
        return list;
    }

    @Override
    public List<String> suggest(SearchRequest request,String suggestName) {
        try {
            SearchResponse response = client.search(request);
            if (response.getSuggest() == null) {
                return null;
            }
            List<String> list = new ArrayList<String>();
            response.getSuggest().getSuggestion(suggestName).getEntries().get(0).getOptions().forEach(item -> list.add(item.getText().string()));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
