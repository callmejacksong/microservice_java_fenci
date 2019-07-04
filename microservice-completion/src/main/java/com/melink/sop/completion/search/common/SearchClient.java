package com.melink.sop.completion.search.common;


import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.search.SearchRequest;

import java.util.List;

public interface SearchClient {

    /**
     * 搜索结果
     */
    List<JSONObject> search(SearchRequest request);

    /**
     * 搜索
     */
    <T> List<T> search(SearchRequest request, Class<T> tClass);


    List<String> suggest(SearchRequest request,String suggestName);

}
