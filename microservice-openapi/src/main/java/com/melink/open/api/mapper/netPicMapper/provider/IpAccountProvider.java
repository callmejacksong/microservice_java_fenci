package com.melink.open.api.mapper.netPicMapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class IpAccountProvider {
    public String findByLimit(@Param("page") Integer page, @Param("size") Integer size) {
        return new SQL() {
            {
                Integer start = (page - 1) * size;
                SELECT("*");
                FROM("ip_account");
                ORDER_BY("display_order limit " + start + ",#{size}");
            }

        }.toString();

    }
}
