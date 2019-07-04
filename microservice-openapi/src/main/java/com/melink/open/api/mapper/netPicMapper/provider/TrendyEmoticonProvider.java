package com.melink.open.api.mapper.netPicMapper.provider;

import com.melink.microservice.utils.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

public class TrendyEmoticonProvider {


    public String findAllTrendingCountByAppId(String appId){
        return new SQL() {
            {
                String dateStr = DateUtils.getDate(new Date());
                SELECT("count(guid)");
                FROM("trendy_emoticon");
                WHERE("app_id = #{appId}");
                AND();
                WHERE("show_date like '%"+dateStr+"%' or show_date is null");
            }
        }.toString();

    }


    public String findTodayTrendyEmoticonsByAppId(@Param("appId") String appId,@Param("count")Long count) {
        return new SQL() {
            {
                String dateStr = DateUtils.getDate(new Date());
                SELECT_DISTINCT("*");
                FROM("trendy_emoticon");
                WHERE("show_date like '%"+dateStr+"%'");
                if (count > 0) {
                    AND();
                    WHERE("app_id = #{appId}");
                }else{
                    AND();
                    WHERE("app_id is null");
                }
            }
        }.toString();
    }

    public String findAllTrendingByAppIdPage(@Param("appId") String appId,@Param("page") Integer page, @Param("size") Integer size) {
        String sql = new SQL() {
            {
                int start = (page - 1) * size;
                String dateStr = DateUtils.getDate(new Date());
                SELECT_DISTINCT("*");
                FROM("trendy_emoticon");
                WHERE("app_id = #{appId}");
                AND();
                WHERE("show_date like '%" + dateStr + "%' or show_date is null");
                ORDER_BY("createdtime desc,guid desc limit " + start + ","+size);
            }
        }.toString();
        return sql;
    }

    public String findAllTrendingByAppId(@Param("appId")String appId){
        return new SQL(){
            {
                SELECT_DISTINCT("*");
                FROM("trendy_emoticon");
                WHERE("app_id = #{appId}");
                AND();
                WHERE("show_date is null");
                ORDER_BY("createdtime desc,guid desc");
            }
        }.toString();
    }
}
