package com.melink.open.api.mapper.netPicMapper.provider;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public class KeywordNetProvider {

    public String findKeywordByKeywordIds(@Param("ids")List<String> ids){
        StringBuilder str = new StringBuilder();
        str.append("select * from keyword_net where guid in(");
        int len = ids.size();
        for (int i = 0 ; i < len; i++) {
            str.append("'" + ids.get(i) + "'");
            if (i < len - 1){
                str.append(",");
            }
        }
        str.append(")");
        return str.toString();


    }
}

