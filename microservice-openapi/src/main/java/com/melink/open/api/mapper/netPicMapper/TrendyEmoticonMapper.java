package com.melink.open.api.mapper.netPicMapper;

import com.melink.open.api.mapper.netPicMapper.provider.TrendyEmoticonProvider;
import com.melink.open.api.model.TrendyEmoticon;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface TrendyEmoticonMapper {

    @Select("select * from trendy_emoticon where app_id is null and show_date is null order by createdtime desc,guid desc")
    List<TrendyEmoticon> findAllTrendingAppIdIsNullAndShowDaetIsNull();


    @SelectProvider(type = TrendyEmoticonProvider.class,method = "findAllTrendingByAppId")
     List<TrendyEmoticon> findAllTrendingByAppId(@Param("appId") String appId);

    /**
     *
     * @param appId
     * @param count 通过appId查询出trendy_emoticon的总条数
     * @return
     */
    @SelectProvider(type = TrendyEmoticonProvider.class,method = "findTodayTrendyEmoticonsByAppId")
    List<TrendyEmoticon> findTodayTrendyEmoticonsByAppId(@Param("appId") String appId, @Param("count") Long count);

    @SelectProvider(type = TrendyEmoticonProvider.class,method = "findAllTrendingByAppIdPage")
    List<TrendyEmoticon> findAllTrendingByAppIdPage(@Param("appId") String appId, @Param("page") Integer page, @Param("size") Integer size);

    @Select("select count(guid) from trendy_emoticon where app_id = #{appId}")
    Long findCountByAppId(@Param("appId") String appId);


    @Select("select t0.* from trendy_emoticon t0  inner join basic_net_picture_textinfo t1 on t0.np_id = t1.guid where t1.textinfo != '' order by t0.createdtime desc limit 100")
    List<TrendyEmoticon> findAllTrendyByTextInfo();

    @Select("select t0.* from trendy_emoticon t0  inner join basic_net_picture_textinfo t1 on t0.np_id = t1.guid where t1.textinfo = '' order by t0.createdtime desc limit 100")
    List<TrendyEmoticon> findAllTrendyNotTextInfo();

}
