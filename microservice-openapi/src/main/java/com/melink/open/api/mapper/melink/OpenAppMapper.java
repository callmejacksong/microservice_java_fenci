package com.melink.open.api.mapper.melink;

import com.melink.open.api.model.OpenApp;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OpenAppMapper {

    @Select("select * from open_app where guid = #{guid}")
    @Results(id = "qq",value = {
            @Result(property = "registrantQQ", column = "registrant_qq")
    })
    OpenApp findByGuid(@Param("guid") String guid);

    @Select("select distinct * from open_app where platform_id = #{platformId} and isshow = true order by createtime limit #{limit}")
    @ResultMap(value = "qq")
    List<OpenApp> findAppsByPlatformId(@Param("platformId") String platformId, @Param("limit") int limit);


}
